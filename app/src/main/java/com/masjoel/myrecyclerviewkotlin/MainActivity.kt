package com.masjoel.myrecyclerviewkotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.masjoel.myrecyclerviewkotlin.adapter.CardViewHeroAdapter
import com.masjoel.myrecyclerviewkotlin.adapter.GridHeroAdapter
import com.masjoel.myrecyclerviewkotlin.adapter.ListHeroAdapter
import com.masjoel.myrecyclerviewkotlin.model.Hero
import com.masjoel.myrecyclerviewkotlin.model.HeroesData
import org.json.JSONArray

class MainActivity : AppCompatActivity() {
    private var hero = arrayListOf<Hero>()
    private var list: ArrayList<Hero> = arrayListOf()
    private lateinit var rvHeroes: RecyclerView
    private var title: String = "Mode List"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setActionBarTitle(title)

        rvHeroes = findViewById(R.id.rv_heroes)
        rvHeroes.setHasFixedSize(true)

        list.addAll(HeroesData.listData)
        showRecyclerList()
//        getDataHeroes()
    }

    private fun getDataHeroes(){
        val url ="http://192.168.0.108/2020/ontukang/apidummy/apphero"
        AndroidNetworking.get(url)
            .setPriority(Priority.LOW)
            .build()
            .getAsJSONArray(object : JSONArrayRequestListener {
                override fun onResponse(response: JSONArray) {
                    for (i in 0 until response.length()) {
                        val jsonObject = response.getJSONObject(i)
                        val hero = Hero()
                        hero.name = jsonObject.getString("heroNames")
                        hero.detail = jsonObject.getString("heroDetails")
                        hero.photo = jsonObject.getInt("heroesImages")
                        list.add(hero)
                    }
                    showList()
                }
                override fun onError(anError: ANError) {}
            })
    }

    private fun showList() {
        rvHeroes.layoutManager = LinearLayoutManager(this)
        val listHeroAdapter = ListHeroAdapter(list)
        rvHeroes.adapter = listHeroAdapter
        listHeroAdapter.setOnItemClickCallback(object : ListHeroAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Hero) {
                showSelectedHero(data)
            }
        })
    }


    private fun showRecyclerGrid() {
        rvHeroes.layoutManager = GridLayoutManager(this, 2)
        val gridHeroAdapter = GridHeroAdapter(list)
        rvHeroes.adapter = gridHeroAdapter
        gridHeroAdapter.setOnItemClickCallback(object : GridHeroAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Hero) {
                showSelectedHero(data)
            }
        })
    }

    private fun showRecyclerList() {
        rvHeroes.layoutManager = LinearLayoutManager(this)
        val listHeroAdapter = ListHeroAdapter(list)
        rvHeroes.adapter = listHeroAdapter
        listHeroAdapter.setOnItemClickCallback(object : ListHeroAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Hero) {
                showSelectedHero(data)
            }
        })
    }
    private fun showRecyclerCardView() {
        rvHeroes.layoutManager = LinearLayoutManager(this)
        val cardViewHeroAdapter = CardViewHeroAdapter(list)
        rvHeroes.adapter = cardViewHeroAdapter
        cardViewHeroAdapter.setOnItemClickCallback(object : CardViewHeroAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Hero) {
                showSelectedHero(data)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        setMode(item.itemId)
        return super.onOptionsItemSelected(item)
    }

    private fun setMode(selectedMode: Int) {
        when(selectedMode){
            R.id.action_list -> {
                title = "List View"
                showRecyclerList()
            }
            R.id.action_grid -> {
                title = "Grid View"
                showRecyclerGrid()
            }
            R.id.action_cardview -> {
                title = "Card View"
                showRecyclerCardView()
            }
        }
        setActionBarTitle(title)
    }

    private fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }

    private fun showSelectedHero(hero: Hero) {
        Toast.makeText(this, "Kamu memilih " + hero.name, Toast.LENGTH_SHORT).show()
        val selectedUser = Hero(
            hero.name,
            hero.detail,
            hero.photo
        )
        val detailIntent = Intent(this@MainActivity, DetailActivity::class.java)
        detailIntent.putExtra(DetailActivity.EXTRA_USER, selectedUser)

        /*val detailIntent = Intent(this@MainActivity, DetailActivity::class.java)
        detailIntent.putExtra(DetailActivity.EXTRA_NAME, hero.name)
        detailIntent.putExtra(DetailActivity.EXTRA_DETAIL, hero.detail)
        detailIntent.putExtra(DetailActivity.EXTRA_PHOTO, hero.photo)*/
        startActivity(detailIntent)
    }

}