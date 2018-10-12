package br.com.popcode.starwarswiki.activities

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.widget.SearchView
import android.widget.Toast
import br.com.popcode.starwarswiki.R
import br.com.popcode.starwarswiki.adapters.ListAdapter
import br.com.popcode.starwarswiki.api.Sw
import br.com.popcode.starwarswiki.helpers.CharacterDao
import br.com.popcode.starwarswiki.helpers.DB
import br.com.popcode.starwarswiki.helpers.EndlessRecyclerViewScrollListener
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

        val characterDao = DB(this).characterDao

        my_toolbar.title = ""
        setSupportActionBar(my_toolbar)

        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        rv_list.layoutManager = linearLayoutManager

        val list: MutableList<Character> = arrayListOf()

        rv_list.adapter = ListAdapter(list, this)

        Sw().getPeople(1, PeopleListener(characterDao, list))

        rv_list.addOnScrollListener(OnScrollListener(linearLayoutManager, characterDao, list))

        fav_button.setOnClickListener {
            list.clear()
            list.addAll(characterDao.getFavs())
            rv_list.adapter.notifyDataSetChanged()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_search, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu!!.findItem(R.id.search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = getString(R.string.search_hint)

        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

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

        }

        override fun onFailure(t: Throwable) {
            Toast.makeText(this@MainActivity, "Falha na conexão com o BD, recuperando dados offline", Toast.LENGTH_SHORT).show()
            list.clear()
            list.addAll(dao.getAll())
            rv_list.adapter.notifyDataSetChanged()
            hasNextPage = false
        }
    }

    private fun filterFavorites(list: MutableList<Character>, dao: CharacterDao){
        list.clear()

        list.addAll(if(favFiltered) dao.getFavs() else dao.getAll())

        rv_list.adapter.notifyDataSetChanged()
        hasNextPage = false
    }

    inner class OnScrollListener(linearLayoutManager: LinearLayoutManager, var characterDao: CharacterDao, var list: MutableList<Character>) : EndlessRecyclerViewScrollListener(linearLayoutManager) {
        override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
            if (hasNextPage) {
                Sw().getPeople(pageCount, PeopleListener(characterDao, list))
                pageCount++
            }
        }
    }


}
