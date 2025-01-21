package com.example.todo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.adapter.TodoAdapter
import com.example.todo.data.TodoItem
import com.example.todo.data.TodoItemsRepository
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TodoListFragment : Fragment() {
    private lateinit var todoAdapter: TodoAdapter
    private lateinit var repository: TodoItemsRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_todo_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        repository = TodoItemsRepository()
        setupRecyclerView()
        setupFab()
    }

    private fun setupRecyclerView() {
        todoAdapter = TodoAdapter { todoItem ->
            // Открываем экран редактирования при клике на элемент
            navigateToEdit(todoItem)
        }

        view?.findViewById<RecyclerView>(R.id.todoRecyclerView)?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = todoAdapter
        }

        updateList()
    }

    private fun setupFab() {
        view?.findViewById<FloatingActionButton>(R.id.addTodoFab)?.setOnClickListener {
            navigateToEdit(null)
        }
    }

    private fun updateList() {
        todoAdapter.submitList(repository.getAllItems())
    }

    private fun navigateToEdit(todoItem: TodoItem?) {
        findNavController().navigate(
            TodoListFragmentDirections.actionListToEdit(todoItem?.id)
        )
    }
} 