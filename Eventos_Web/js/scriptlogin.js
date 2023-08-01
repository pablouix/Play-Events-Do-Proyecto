document.getElementById("loginForm").addEventListener("submit", function(event) {
    event.preventDefault();

    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;

    var xhr = new XMLHttpRequest();
    var url = "http://eventos.somee.com/api/Usuarios";
    xhr.open("GET", url, true);
    xhr.setRequestHeader("Content-Type", "application/json");

    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                var users = JSON.parse(xhr.responseText);
                var isValidLogin = false;

                for (var i = 0; i < users.length; i++) {
                    var user = users[i];
                    if (user.nombre === username && user.contrasena === password) {
                        isValidLogin = true;
                        break;
                    }
                }

                if (isValidLogin) {
                    window.location.href = "eventos.html";
                } else {
                    alert("Credenciales inválidas. Inténtalo de nuevo.");
                }
            } else {
                alert("Error al conectarse con el servidor. Inténtalo de nuevo más tarde.");
            }
        }
    };

    xhr.send();
});

function goToIndex() {
    window.location.href = "../index.html";
}
