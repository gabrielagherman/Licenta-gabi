const apiUrl = 'http://3.70.217.13:8003/mycalcwebapp';

function calc(first, second, op) {
    let data = {first: first, second: second, op: op};
    let csrfToken = getCookie('csrfToken');

    fetch(apiUrl + '/calc', {
        method: 'POST',
        credentials: 'include',
        body: JSON.stringify(data),
        headers: {
            'Content-Type': 'application/json',
            'X-CSRF-Token': csrfToken
        }
    })
    .then(response => {
        if (response.ok) {
            return response.json();
        } else if (response.status === 401) {
            let original = encodeURIComponent(window.location.pathname + window.location.search);
            window.location.replace('login.html?backUrl=' + original);
        } else {
            throw Error(response.statusText);
        }
    })
    .then(json => document.getElementById("result").innerHTML = json.result)
    .catch(error => console.error('Error: ', error));
}

function getCookie(cookieName) {
    var cookieValue = document.cookie.split(';')
        .map(item => item.split('=')
            .map(x => decodeURIComponent(x.trim())))
        .filter(item => item[0] === cookieName)[0]

    if (cookieValue) {
        return cookieValue[1];
    }
}

window.addEventListener('load', function(e) {
    document.getElementById('calc')
        .addEventListener('submit', processFormSubmit);
});

function processFormSubmit(e) {
    e.preventDefault();

    let first = document.getElementById('first').value;
    let second = document.getElementById('second').value;
    let op = document.getElementById('op').value;

    calc(first, second, op);

    return false;
}
