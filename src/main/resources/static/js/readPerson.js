const params = new URLSearchParams(window.location.search);

// Obtains ID and passes it as a parameter to getData()
for (let param of params) {
    console.log("Object ID: " + param)
    let id = param[1];
    console.log(id);
    getData(id)
}

function getData(id) {
    fetch('http://localhost:9092/person/read/' + id)
        .then(
            function (response) {
                if (response.status !== 200) {
                    console.log('Looks like there was a problem. Status Code: ' +
                        response.status);
                    return;
                }

                response.json().then(function (data) {
                    console.log("MY DATA OBJ", data)

                    document.querySelector("input#id").value = data.id
                    document.querySelector("input#name").value = data.name


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

        let data = {
            "id": id,
            "name": name

        }
        console.log("Data to post", data)
        console.log(id)

        sendData(data, id)
    });


function sendData(data, id) {
    fetch("http://localhost:9092/person/update/" + id, {
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
            successParagraph.innerText = "Person Updated!\nClick Here To Go Back"
            document.querySelector("div#success").appendChild(successParagraph)

        })
        .catch(function (error) {
            console.log('Request failed', error);
        });
}
