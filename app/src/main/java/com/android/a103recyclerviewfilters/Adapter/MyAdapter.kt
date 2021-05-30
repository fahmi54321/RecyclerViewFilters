package com.android.a103recyclerviewfilters.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.a103recyclerviewfilters.Model.DataItem
import com.android.a103recyclerviewfilters.Model.Item
import com.android.a103recyclerviewfilters.Model.ResponseData
import com.android.a103recyclerviewfilters.R

class MyAdapter(
    internal var context: Context,
    internal var itemList:List<Item>
):RecyclerView.Adapter<MyAdapter.MyViewHolder>(),Filterable {

    internal var filterListResult:List<Item>
    init {
        this.filterListResult = itemList
    }

    class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {

        internal var  txt_title:TextView
        internal var  txt_url:TextView

        init {
            txt_title = itemView.findViewById(R.id.title)
            txt_url = itemView.findViewById(R.id.url)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_layout,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.txt_title.text = filterListResult.get(position).title
        holder.txt_url.text = filterListResult.get(position).url
    }

    override fun getItemCount(): Int {
        return filterListResult.size
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(charString: CharSequence?): FilterResults {
                var charSearch = charString.toString()
                if (charString!!.isEmpty()){
                    filterListResult = itemList
                }else{
                    val resultList = ArrayList<Item>()
                    for (row in itemList){
                        if (row.title?.toLowerCase()?.contains(charSearch.toLowerCase())!!){
                            resultList.add(row)
                        }
                    }
                    filterListResult = resultList
                }

                val filterResults = FilterResults()
                filterResults.values = filterResults
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults?) {
                filterListResult = filterResults?.values as List<Item>
                notifyDataSetChanged()
            }

        }
    }
}