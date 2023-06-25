function generateList() {
  const lista = document.getElementById("lista");
  lista.innerHTML = "";

  fetch("http://eventos.somee.com/api/Eventos")
    .then(response => response.json())
    .then(data => {
      data.forEach(evento => {
        const eventoLi = document.createElement("li");

        const nombreP = document.createElement("p");
        nombreP.textContent = evento.nombre;
        eventoLi.appendChild(nombreP);

        const fechaP = document.createElement("p");
        const fecha = new Date(evento.fecha);
        fechaP.textContent = "Fecha: " + fecha.toLocaleDateString();
        eventoLi.appendChild(fechaP);

        const horaP = document.createElement("p");
        horaP.textContent = "Hora: " + evento.hora;
        eventoLi.appendChild(horaP);

        lista.appendChild(eventoLi);

        const zonasUl = document.createElement("ul");
        zonasUl.classList.add("hidden");
        evento.zonaDetalles.forEach((zona, index) => {
          const zonaLi = document.createElement("li");

          const nombreLi = document.createElement("li");
          nombreLi.textContent = "Zona: " + zona.nombre;
          zonaLi.appendChild(nombreLi);

          const capacidadLi = document.createElement("li");
          capacidadLi.textContent = "Capacidad: " + zona.capacidad + " Personas";
          zonaLi.appendChild(capacidadLi);

          const precioLi = document.createElement("li");
          precioLi.textContent = "Precio: " + zona.precio + " DOP";
          zonaLi.appendChild(precioLi);

          zonasUl.appendChild(zonaLi);
        });

        eventoLi.appendChild(zonasUl);

        const toggleButton = document.createElement("button");
        toggleButton.textContent = "Mostrar Zona";
        eventoLi.appendChild(toggleButton);

        toggleButton.addEventListener("click", () => {
          zonasUl.classList.toggle("hidden");
          if (toggleButton.textContent === "Mostrar Zona") {
            toggleButton.textContent = "Ocultar Zona";
          } else {
            toggleButton.textContent = "Mostrar Zona";
          }
        });
      });
    })
    .catch(error => {
      console.error("Error al obtener los datos de la API:", error);
    });
}

generateList();