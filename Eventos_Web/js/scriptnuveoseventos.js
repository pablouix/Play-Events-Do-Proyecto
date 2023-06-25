function generateEventCards() {
const eventosLista = document.getElementById("eventos-lista");

fetch("http://eventos.somee.com/api/Eventos")
    .then(response => response.json())
    .then(data => {
    const ultimosEventos = data.slice(-3);

    ultimosEventos.forEach(evento => {
        const cardDiv = document.createElement("div");
        cardDiv.classList.add("card");

        const nombreP = document.createElement("p");
        nombreP.textContent = evento.nombre;
        cardDiv.appendChild(nombreP);

        const fechaP = document.createElement("p");
        const fecha = new Date(evento.fecha);
        fechaP.textContent = "Fecha: " + fecha.toLocaleDateString();
        cardDiv.appendChild(fechaP);

        const horaP = document.createElement("p");
        horaP.textContent = "Hora: " + evento.hora;
        cardDiv.appendChild(horaP);

        const buyButton = document.createElement("button");
        buyButton.textContent = "Comprar Boletas";
        buyButton.classList.add("buy-button");
        cardDiv.appendChild(buyButton);

        eventosLista.appendChild(cardDiv);
    });
    })
    .catch(error => {
    console.error("Error al obtener los datos de la API:", error);
    });
}

generateEventCards();

