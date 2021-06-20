package com.cabegaira.lab05

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

class RecyclerView_Adapter_Students(private var items: ArrayList<Students>): RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    Filterable {

    var itemsList: ArrayList<Students>? = null

    lateinit var mcontext: Context

    class PersonHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    init {
        this.itemsList = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val personListView = LayoutInflater.from(parent.context).inflate(R.layout.template_students, parent, false)
        val sch = PersonHolder(personListView)
        mcontext = parent.context
        return sch
    }

    override fun getItemCount(): Int {
        return itemsList?.size!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = itemsList?.get(position)
        holder.itemView.findViewById<TextView>(R.id.tvName)?.text = item?.name +" "+item?.lastname
        holder.itemView.findViewById<TextView>(R.id.tvId)?.text = item?.id.toString()
        holder.itemView.findViewById<ImageView>(R.id.ivFoto).setImageResource(R.drawable.student)

        holder.itemView.setOnClickListener {
            val intent = Intent(this.mcontext, GetMatricula::class.java)
            intent.putExtra("student", item?.id)
            this.mcontext.startActivity(intent)
        }
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    itemsList = items
                } else {
                    val resultList = ArrayList<Students>()
                    for (row in items) {
                        if (row.name.toLowerCase().contains(charSearch.toLowerCase())) {
                            resultList.add(row)
                        }
                        if (row.lastname.toLowerCase().contains(charSearch.toLowerCase())) {
                            resultList.add(row)
                        }
                        if (row.age.toString().toLowerCase().contains(charSearch.toLowerCase())) {
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
                itemsList = results?.values as ArrayList<Students>
                notifyDataSetChanged()
            }

        }
    }
}

