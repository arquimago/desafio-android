package br.com.popcode.starwarswiki.Activities

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.widget.SearchView
import android.widget.Toast
import br.com.popcode.starwarswiki.Adapters.ListAdapter
import br.com.popcode.starwarswiki.Api.Sw
import br.com.popcode.starwarswiki.Models.Character
import br.com.popcode.starwarswiki.R
import com.orhanobut.hawk.Hawk
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Hawk.init(this).build()

        my_toolbar.title = ""
        setSupportActionBar(my_toolbar)

        rv_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val list: MutableList<Character> = arrayListOf()
        rv_list.adapter = ListAdapter(list)

        Sw().getPeople(PeopleListener(list))

        fav_star.setOnClickListener{
            list.sortWith(compareBy{ it.favorite })
            list.reverse()
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
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
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

    inner class PeopleListener(var list: MutableList<Character>) : Sw.PeopleListener{
        override fun onResponse(char: Character) {
            list.add(char)
            rv_list.adapter.notifyDataSetChanged()
        }

        override fun onFailure(t: Throwable) {
            Toast.makeText(this@MainActivity,t.localizedMessage,Toast.LENGTH_SHORT).show()
        }
    }


}
