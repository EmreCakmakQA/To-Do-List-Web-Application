fetch('http://localhost:9092/person/read/')
    .then(
        function (response) {
            if (response.status !== 200) {
                console.log('Looks like there was a problem. Status Code: ' +
                    response.status);
                return;
            }


            response.json().then(function (data) {
                displayData(data)
            });
        }
    )
    .catch(function (err) {
        console.log('Fetch Error :-S', err);
    });

function displayData(object) {
    for (let data of object) {

        let todos = document.createElement("ul");
        todos.setAttribute("id", "listOfTodos")


        for (let i = 0; i < data.todos.length; i++) {
            console.log(data.todos[i].name)

            let todoList = document.createElement("li")
            todoList.setAttribute("id", "tasks")
            let status = "";
            data.todos[i].isComplete == false ? status = "not complete ❌" : status = "completed ✔️";
            todoList.innerText = data.todos[i].name + "  :  " + status;

            let viewBtnT = document.createElement("a");
            viewBtnT.setAttribute("class", "btn btn-warning btn-sm")


            let delBtnT = document.createElement("button");
            delBtnT.setAttribute("class", "btn btn-danger btn-sm")

            delBtnT.innerText = "Del"
            delBtnT.onclick = function () {
                deleteTodoByid(data.todos[i].id); return false;
            }


            viewBtnT.href = "readTodo.html?id=" + data.todos[i].id
            viewBtnT.innerText = "update"


            todos.appendChild(todoList);
            todos.appendChild(viewBtnT)
            todos.appendChild(delBtnT)

        }


        // Create card

        let cardBody = document.createElement("div");
        cardBody.setAttribute("class", "card-body");

        let personId = document.createElement("h6");
        personId.setAttribute("class", "card-title");

        let personName = document.createElement("h4");
        personName.setAttribute("class", "card-text")


        // View/Update and Del Button
        let viewBtn = document.createElement("a");

        let updatePersonBtn = document.createElement("a");
        updatePersonBtn.setAttribute("class", "btn btn-warning btn-sm")

        let delBtn = document.createElement("button");
        delBtn.setAttribute("class", "btn btn-danger btn-sm")

        updatePersonBtn.innerText = "Update"
        delBtn.innerText = "Delete"




        // Insert data into elements
        personId.innerText = data.id;
        personName.innerText = data.name;
        todoStatus = data.isComplete;

        // Append elements into card div
        cardBody.appendChild(personId)
        cardBody.appendChild(personName)
        cardBody.appendChild(todos);
        cardBody.appendChild(updatePersonBtn)
        cardBody.appendChild(delBtn);
        card.appendChild(cardBody)

        // View/Update
        updatePersonBtn.href = "readPerson.html?id=" + data.id

        // Delete
        delBtn.onclick = function () {
            console.log(data.id);
            deletePersonByid(data.id); return false;
        }


    }
}

//////////////   Create //////////////
// Create a todo
document
    .querySelector("form.viewRecord")
    .addEventListener("submit", function (stop) {
        stop.preventDefault();
        let formElements = document.querySelector("form.viewRecord").elements;
        console.log(formElements)


        let name = formElements["name"].value;
        let personID = formElements["personName"].value;

        let data = {

            "name": name,
            "isComplete": false,
            "person": {
                "id": personID
            }
        }
        console.log("Data to post", data)


        sendData(data)
    });


function sendData(data) {
    fetch("http://localhost:9092/todo/create", {
        method: 'post',
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        },
        body: JSON.stringify(data)
    })
        .then(function (data) {
            console.log('Request succeeded with JSON response', data);
            console.log("created!");
            location.reload();
        })
        .catch(function (error) {
            console.log('Request failed', error);
        });
}
// End of create a todo

// Creating a Person 
document
    .querySelector("form.viewRecordPeople")
    .addEventListener("submit", function (stop) {
        stop.preventDefault();
        let formElements = document.querySelector("form.viewRecordPeople").elements;
        console.log(formElements)


        let personName = formElements["newPersonName"].value;

        let data = {
            "name": personName
        }
        console.log("Data to post", data)


        createPerson(data)
    });

function createPerson(data) {
    fetch("http://localhost:9092/person/create", {
        method: 'post',
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        },
        body: JSON.stringify(data)
    })
        .then(function (data) {
            console.log('Request succeeded with JSON response', data);
            console.log("created!");
            location.reload();
        })
        .catch(function (error) {
            console.log('Request failed', error);
        });
}
// End of create person





//////////////   Delete //////////////
// Delete person
function deletePersonByid(id) {
    fetch("http://localhost:9092/person/delete/" + id, {
        method: 'delete',
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        },
    }).then(function (response) {
        if (response.status !== 200) {
            location.reload();
            return;
        }

        response.json().then(function (response) {
            console.log("Expecting error code to be 204 when deleting: " + response.status);

        });
    })


}
// End of delete person

// Delete todo
function deleteTodoByid(id) {
    fetch("http://localhost:9092/todo/delete/" + id, {
        method: 'delete',
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        },
    }).then(function (response) {
        if (response.status !== 200) {
            location.reload();
            return;
        }

        response.json().then(function (response) {
            console.log("Expecting error code to be 204 when deleting: " + response.status);

        });
    })


}
// End of delete todo