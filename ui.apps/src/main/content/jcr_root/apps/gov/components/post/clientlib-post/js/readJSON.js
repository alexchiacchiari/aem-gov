(async function (document){
    let ul = document.getElementById("posts");
    let xhr = new XMLHttpRequest();
        
    xhr.open("GET", "https://jsonplaceholder.typicode.com/posts", true);
    xhr.setRequestHeader("content-type", "application/json");
    xhr.send();
    xhr.onerror = function() {
        alert("Error API");
    }
    xhr.onreadystatechange = function(evt) {
        if(xhr.readyState === 4 && xhr.status === 200) {
            let jsonObj = JSON.parse(evt.target.response);
            console.log(jsonObj);
            jsonObj.forEach(function (ele) {
                let li = document.createElement("li");
                li.innerText = ele.title;
                ul.appendChild(li);
            })
        } else {
            console.log("zi");
        }
    }
}(document));