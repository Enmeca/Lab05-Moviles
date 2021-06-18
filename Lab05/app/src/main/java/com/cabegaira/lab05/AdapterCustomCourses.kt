package com.cabegaira.lab05

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

class RecyclerView_Adapter_Courses(private var items: ArrayList<Courses>): RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    Filterable {

    var itemsList: ArrayList<Courses>? = null

    lateinit var mcontext: Context

    class PersonHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    init {
        this.itemsList = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val personListView = LayoutInflater.from(parent.context).inflate(R.layout.template_courses, parent, false)
        val sch = PersonHolder(personListView)
        mcontext = parent.context
        return sch
    }

    override fun getItemCount(): Int {
        return itemsList?.size!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = itemsList?.get(position)
        holder.itemView.findViewById<TextView>(R.id.tvId)?.text = item?.id.toString()
        holder.itemView.findViewById<TextView>(R.id.tvdesc)?.text = item?.description
        holder.itemView.findViewById<TextView>(R.id.tvCred)?.text= item?.credits.toString()
        holder.itemView.findViewById<ImageView>(R.id.ivFoto).setImageResource(R.drawable.student)

        /*holder.itemView.setOnClickListener {
            val intent = Intent(this.mcontext, EditAplication::class.java)
            intent.putExtra("dato", item)
            intent.putExtra("position",position)
            this.mcontext.startActivity(intent)
        }*/
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    itemsList = items
                } else {
                    val resultList = ArrayList<Courses>()
                    for (row in items) {
                        if (row.id.toString().contains(charSearch.toLowerCase())) {
                            resultList.add(row)
                        }
                        if (row.description.toLowerCase().contains(charSearch.toLowerCase())) {
                            resultList.add(row)
                        }
                        if (row.credits.toString().toLowerCase().contains(charSearch.toLowerCase())) {
                            resultList.add(row)
                        }
                    }
                    itemsList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = itemsList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                itemsList = results?.values as ArrayList<Courses>
                notifyDataSetChanged()
            }

        }
    }
}

