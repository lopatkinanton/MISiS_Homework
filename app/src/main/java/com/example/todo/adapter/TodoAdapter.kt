import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.data.TodoItem

class TodoAdapter(
    private val onItemClick: (TodoItem) -> Unit
) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {
    
    private var items: List<TodoItem> = emptyList()

    fun submitList(newItems: List<TodoItem>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_todo, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class TodoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val statusIndicator: ImageView = view.findViewById(R.id.statusIndicator)
        private val todoText: TextView = view.findViewById(R.id.todoText)

        fun bind(item: TodoItem) {
            statusIndicator.isSelected = item.isCompleted
            todoText.text = item.text
            itemView.setOnClickListener { onItemClick(item) }
        }
    }
}