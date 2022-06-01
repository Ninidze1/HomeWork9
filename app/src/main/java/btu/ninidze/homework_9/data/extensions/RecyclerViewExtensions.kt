package btu.ninidze.homework_9.data.extensions

import androidx.recyclerview.widget.RecyclerView

inline fun <reified T : RecyclerView.Adapter<*>> RecyclerView.getRecyclerViewAdapter(): T {
    return this.adapter as T
}