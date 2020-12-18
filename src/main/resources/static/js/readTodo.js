const params = new URLSearchParams(window.location.search);

// Obtains ID and passes it as a parameter to getData()
for (let param of params) {
    console.log("Object ID: " + param)
    let id = param[1];
    console.log(id);
    getData(id)
}

function getData(id) {
    fetch('http://localhost:9092/todo/read/' + id)
        .then(
            function (response) {
                if (response.status !== 200) {
                    console.log('Looks like there was a problem. Status Code: ' +
                        response.status);
                    return;
                }
                // Examine the text in the response
                response.json().then(function (data) {
                    console.log("MY DATA OBJ", data)

                    document.querySelector("input#id").value = data.id
                    document.querySelector("input#name").value = data.name
                    document.querySelector("input#isComplete").value = data.isComplete
                    document.querySelector("input#person_id").value = data.person

                });
            }
        )
        .catch(function (err) {
            console.log('Fetch Error :-S', err);
        });
}


document
    .querySelector("form.viewRecord")
    .addEventListener("submit", function (stop) {
        stop.preventDefault();
        let formElements = document.querySelector("form.viewRecord").elements;
        console.log(formElements)

        let id = formElements["id"].value;
        let name = formElements["name"].value;
        let isComplete = formElements["isComplete"].value;
        let person = formElements["person_id"].value;

        let data = {
            "id": id,
            "name": name,
            "isComplete": isComplete,
            "person": {
                "id": person
            }
        }
        console.log("Data to post", data)
        console.log(id)

        sendData(data, id)
    });


function sendData(data, id) {
    fetch("http://localhost:9092/todo/update/" + id, {
        method: 'put',
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        },
        body: JSON.stringify(data)
    })
        .then(function (data) {
            console.log('Request succeeded with JSON response', data);
            let successParagraph = document.createElement("a");
            successParagraph.href = "index.html";
            successParagraph.className = "btn btn-success btn-block";
            successParagraph.innerText = "Todo Updated!\nClick Here To Go Back"
            document.querySelector("div#success").appendChild(successParagraph)

        })
        .catch(function (error) {
            console.log('Request failed', error);
        });
}

function deleteByid(id) {
    fetch("http://localhost:9092/todo/delete/" + id, {
        method: 'delete',
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        },
    })


}