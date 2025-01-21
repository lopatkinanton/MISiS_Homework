package com.example.todo.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.todo.R
import com.example.todo.data.TodoItem
import com.example.todo.data.TodoItemsRepository
import java.util.Calendar
import java.util.UUID

class TodoEditFragment : Fragment() {
    private lateinit var repository: TodoItemsRepository
    private var todoItem: TodoItem? = null
    private var selectedDeadline: Long? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_todo_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        repository = TodoItemsRepository()

        arguments?.getString("todoId")?.let { id ->
            todoItem = repository.getAllItems().find { it.id == id }
        }

        selectedDeadline = savedInstanceState?.getLong("selected_deadline")

        setupViews()
        setupButtons()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        selectedDeadline?.let {
            outState.putLong("selected_deadline", it)
        }
    }

    private fun setupViews() {
        view?.findViewById<EditText>(R.id.todoText)?.setText(todoItem?.text)
        
        // Настраиваем спиннер с приоритетами
        val priorities = TodoItem.Priority.values()
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            priorities.map { TodoItem.Priority.toString(it) }
        )
        view?.findViewById<Spinner>(R.id.prioritySpinner)?.adapter = adapter
        
        // Устанавливаем текущий приоритет если редактируем
        todoItem?.priority?.let { priority ->
            val position = priorities.indexOf(priority)
            view?.findViewById<Spinner>(R.id.prioritySpinner)?.setSelection(position)
        }
    }

    private fun setupButtons() {
        // Настраиваем кнопку дедлайна
        view?.findViewById<Button>(R.id.deadlineButton)?.setOnClickListener {
            showDatePicker()
        }

        // Кнопка сохранения
        view?.findViewById<Button>(R.id.saveButton)?.setOnClickListener {
            saveTodoItem()
        }

        // Кнопка удаления
        view?.findViewById<Button>(R.id.deleteButton)?.setOnClickListener {
            todoItem?.let { 
                repository.deleteItem(it.id)
            }
            findNavController().navigateUp()
        }

        // Кнопка отмены
        view?.findViewById<Button>(R.id.cancelButton)?.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            requireContext(),
            { _, year, month, day ->
                calendar.set(year, month, day)
                selectedDeadline = calendar.timeInMillis
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun saveTodoItem() {
        val text = view?.findViewById<EditText>(R.id.todoText)?.text.toString()
        val priorityString = view?.findViewById<Spinner>(R.id.prioritySpinner)
            ?.selectedItem.toString()
        val priority = TodoItem.Priority.fromString(priorityString)

        val newTodoItem = TodoItem(
            id = todoItem?.id ?: UUID.randomUUID().toString(),
            text = text,
            priority = priority,
            deadline = selectedDeadline,
            isCompleted = todoItem?.isCompleted ?: false,
            modifiedAt = System.currentTimeMillis()
        )

        if (todoItem == null) {
            repository.addItem(newTodoItem)
        } else {
            repository.updateItem(newTodoItem)
        }

        findNavController().navigateUp()
    }
} 