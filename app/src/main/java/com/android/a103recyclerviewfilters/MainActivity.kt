package com.android.a103recyclerviewfilters

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.a103recyclerviewfilters.Adapter.MyAdapter
import com.android.a103recyclerviewfilters.Model.DataItem
import com.android.a103recyclerviewfilters.Model.Item
import com.android.a103recyclerviewfilters.Retrofit.IMyApi
import com.android.a103recyclerviewfilters.Retrofit.RetrofitClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    lateinit var myApi: IMyApi
    lateinit var adapter: MyAdapter
    var compositeDisposable = CompositeDisposable()

    var dataList : MutableList<Item> = ArrayList()
    var searchView:SearchView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title= "Filters"

        val retrofit = RetrofitClient.getInstance
        myApi = retrofit.create(IMyApi::class.java)

        recycler_view.setHasFixedSize(true)
        recycler_view.layoutManager = LinearLayoutManager(this)

        adapter = MyAdapter(this,dataList)
        recycler_view.adapter = adapter

        fetchData()

    }

    private fun fetchData() {
        compositeDisposable.add(
            myApi.getData
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{photos->
                    displayData(photos)
                }
        )
    }

    private fun displayData(photos: List<Item>?) {
        dataList.clear()
        dataList.addAll(photos!!)
        adapter.notifyDataSetChanged()
    }

    override fun onStop() {
        compositeDisposable.clear()
        super.onStop()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu?.findItem(R.id.actiom_search)?.actionView as SearchView
        searchView?.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView?.maxWidth = Int.MAX_VALUE

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                adapter.filter.filter(p0)
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                adapter.filter.filter(p0)
                return false
            }

        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        return if (id == R.id.actiom_search){
            true
        }else{
            super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        if (!searchView?.isIconified!!){
            searchView?.isIconified = true
            return
        }
        super.onBackPressed()
    }
}
