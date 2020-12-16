document
    .querySelector("form.viewRecord")
    .addEventListener("submit", function (stop) {
        stop.preventDefault();
        let formElements = document.querySelector("form.viewRecord").elements;
        console.log(formElements)


        let name = formElements["name"].value;
        let isComplete = formElements["isComplete"].value;
        let person_id = formElements["person_id"].value;

        let data = {

            "name": name,
            "isComplete": isComplete,
            "person": {
                "id": person_id
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
            let successParagraph = document.createElement("a");
            successParagraph.href = "readAll.html";
            successParagraph.className = "btn btn-success btn-block";
            successParagraph.innerText = "Book Created!\nClick Here To Go Back"
            document.querySelector("div#success").appendChild(successParagraph)
        })
        .catch(function (error) {
            console.log('Request failed', error);
        });
}