document.addEventListener('DOMContentLoaded', () => {
    const taskInput = document.getElementById('taskInput');
    const addTaskBtn = document.getElementById('addTask');
    const taskList = document.getElementById('taskList');
    const taskCount = document.getElementById('taskCount');
    const clearCompletedBtn = document.getElementById('clearCompleted');
    const filterButtons = document.querySelectorAll('.filter-section button');
    let tasks = [];
    let currentFilter = 'all';

    addTaskBtn.addEventListener('click', addTask);
    taskInput.addEventListener('keypress', (e) => {
        if (e.key === 'Enter') addTask();
    });
    clearCompletedBtn.addEventListener('click', clearCompleted);

    filterButtons.forEach(button => {
        button.addEventListener('click', () => {
            currentFilter = button.id.replace('filter', '').toLowerCase();
            filterButtons.forEach(btn => btn.classList.remove('active'));
            button.classList.add('active');
            renderTasks();
        });
    });

    function addTask() {
        const taskText = taskInput.value.trim();
        if (taskText !== '') {
            tasks.push({ text: taskText, completed: false });
            taskInput.value = '';
            renderTasks();
        }
    }

    function renderTasks() {
        taskList.innerHTML = '';
        let visibleTasks = tasks;
        if (currentFilter === 'active') {
            visibleTasks = tasks.filter(task => !task.completed);
        } else if (currentFilter === 'completed') {
            visibleTasks = tasks.filter(task => task.completed);
        }

        visibleTasks.forEach((task, index) => {
            const li = document.createElement('li');
            li.innerHTML = `
                <span class="task-content">
                    <span class="task-index">${index + 1}.</span>
                    <span class="task-text">${task.text}</span>
                </span>
                <span class="task-actions">
                    <button class="finish-btn"><i class="fas fa-check"></i></button>
                    <button class="delete-btn"><i class="fas fa-trash"></i></button>
                </span>
            `;
            if (task.completed) {
                li.classList.add('finished');
            }
            taskList.appendChild(li);

            li.querySelector('.finish-btn').addEventListener('click', () => {
                task.completed = !task.completed;
                renderTasks();
            });

            li.querySelector('.delete-btn').addEventListener('click', () => {
                tasks.splice(tasks.indexOf(task), 1);
                renderTasks();
            });
        });

        updateTaskCount();
    }

    function updateTaskCount() {
        const remainingTasks = tasks.filter(task => !task.completed).length;
        taskCount.textContent = `${remainingTasks} task${remainingTasks !== 1 ? 's' : ''} remaining`;
    }

    function clearCompleted() {
        tasks = tasks.filter(task => !task.completed);
        renderTasks();
    }

    renderTasks();
});