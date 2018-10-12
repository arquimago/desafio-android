package br.com.popcode.starwarswiki.activities

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.widget.SearchView
import android.widget.Toast
import br.com.popcode.starwarswiki.R
import br.com.popcode.starwarswiki.adapters.ListAdapter
import br.com.popcode.starwarswiki.api.FavFunctions
import br.com.popcode.starwarswiki.api.Sw
import br.com.popcode.starwarswiki.helpers.CharacterDao
import br.com.popcode.starwarswiki.helpers.DB
import br.com.popcode.starwarswiki.models.Character
import com.orhanobut.hawk.Hawk
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var hasNextPage = true
    var pageCount = 2
    var favFiltered = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Hawk.init(this).build()
        if(Hawk.contains("fav_failed")){
            FavFunctions(this).favoriteChar(Hawk.get("fav_failed"),Hawk.get("name_failed"))
            Hawk.delete("fav_failed")
            Hawk.delete("name_failed")
        }

        val characterDao = DB(this).characterDao

        my_toolbar.title = ""
        setSupportActionBar(my_toolbar)

        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        rv_list.layoutManager = linearLayoutManager

        val list: MutableList<Character> = arrayListOf()

        rv_list.adapter = ListAdapter(list, this)

        Sw().getPeople(1, PeopleListener(characterDao, list))

        fav_button.setOnClickListener {
            filterFavorites(characterDao)
        }
    }

    override fun onResume() {
        super.onResume()
        rv_list.adapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_search, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu!!.findItem(R.id.search).actionView as SearchView

        var dao = DB(this).characterDao

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = getString(R.string.search_hint)

        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                if(query != null) {

                    val text = "%$query%"

                    val qList = dao.searchByName(text)
                    rv_list.adapter = ListAdapter(qList, this@MainActivity)
                    rv_list.adapter.notifyDataSetChanged()
                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                //do nothing

                return true
            }

        })

        return super.onCreateOptionsMenu(menu)
    }

    inner class PeopleListener(var dao: CharacterDao, var list: MutableList<Character>) : Sw.PeopleListener {

        override fun onResponse(next: String?, chars: MutableList<Character>) {
            hasNextPage = (next != null)
            list.addAll(chars)
            rv_list.adapter.notifyDataSetChanged()

            chars.forEach { character ->
                val tempChar = dao.findByName(character.name!!)
                if (tempChar == null) dao.insert(character)
                else {
                    character.favorite = tempChar.favorite
                    rv_list.adapter.notifyDataSetChanged()
                }
            }

            if(hasNextPage){
                Sw().getPeople(pageCount, PeopleListener(dao, list))
                pageCount++
            } else {
                Toast.makeText(this@MainActivity,"Lista Completa", Toast.LENGTH_SHORT).show()
            }

        }

        override fun onFailure(t: Throwable) {
            Toast.makeText(this@MainActivity, "Falha na conexão com o BD, recuperando dados offline", Toast.LENGTH_SHORT).show()
            list.clear()
            list.addAll(dao.getAll())
            rv_list.adapter.notifyDataSetChanged()
            hasNextPage = false
        }
    }

    private fun filterFavorites(dao: CharacterDao){

        val favlist : MutableList<Character> = if(favFiltered) dao.getFavs(true) else dao.getAll()

        fav_button.text = if(favFiltered) "TODOS" else "FAVORITOS"

        rv_list.adapter = ListAdapter(favlist, this@MainActivity)

        favFiltered = !favFiltered

        rv_list.adapter.notifyDataSetChanged()
        hasNextPage = false
    }

}
