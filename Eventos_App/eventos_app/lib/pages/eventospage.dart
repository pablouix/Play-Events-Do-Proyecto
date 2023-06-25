import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;

class EventosPage extends StatefulWidget {
  const EventosPage({Key? key}) : super(key: key);

  @override
  _EventosPageState createState() => _EventosPageState();
}

class _EventosPageState extends State<EventosPage> {
  List<Evento> eventos = [];
  Set<int> expandedZones = {}; 

  @override
  void initState() {
    super.initState();
    fetchData();
  }

  Future<void> fetchData() async {
    final response = await http.get(Uri.parse('http://eventos.somee.com/api/Eventos'));
    if (response.statusCode == 200) {
      final data = json.decode(response.body);
      final List<Evento> fetchedEventos = [];
      for (final eventoData in data) {
        final List<ZonaDetalle> zonaDetalles = [];
        for (final zonaData in eventoData['zonaDetalles']) {
          zonaDetalles.add(
            ZonaDetalle(
              id: zonaData['id'],
              idEvento: zonaData['idEvento'],
              nombre: zonaData['nombre'],
              capacidad: zonaData['capacidad'],
              precio: zonaData['precio'].toDouble(),
            ),
          );
        }
        fetchedEventos.add(
          Evento(
            id: eventoData['id'],
            nombre: eventoData['nombre'],
            fecha: eventoData['fecha'],
            zonaDetalles: zonaDetalles,
          ),
        );
      }
      setState(() {
        eventos = fetchedEventos;
      });
    }
  }

  void toggleZone(int zonaId) {
    setState(() {
      if (expandedZones.contains(zonaId)) {
        expandedZones.remove(zonaId);
      } else {
        expandedZones.add(zonaId);
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Eventos Page'),
      ),
      body: ListView.builder(
        itemCount: eventos.length,
        itemBuilder: (BuildContext context, int index) {
          final evento = eventos[index];
          return Column(
            children: [
              ListTile(
                title: Text(evento.nombre),
                subtitle: Text(evento.fecha),
                trailing: ElevatedButton(
                  onPressed: () {
                    toggleZone(index); // Toggle la visibilidad de la zona al presionar el botón
                  },
                  style: ButtonStyle(
                    backgroundColor: MaterialStateProperty.all<Color>(Colors.blue),
                  ),
                  child: const Text(
                    'Comprar Boletas',
                    style: TextStyle(
                      color: Colors.white,
                    ),
                  ),
                ),
              ),
              if (expandedZones.contains(index)) // Mostrar la información de la zona si está expandida
                Column(
                  children: evento.zonaDetalles.map((zona) {
                    return ListTile(
                      title: Text(zona.nombre),
                      subtitle: Text('Capacidad ${zona.capacidad} Personas, Precio ${zona.precio.toStringAsFixed(2)} DOP'),
                    );
                  }).toList(),
                ),
            ],
          );
        },
      ),
    );
  }
}

class Evento {
  final int id;
  final String nombre;
  final String fecha;
  final List<ZonaDetalle> zonaDetalles;

  Evento({
    required this.id,
    required this.nombre,
    required this.fecha,
    required this.zonaDetalles,
  });
}

class ZonaDetalle {
  final int id;
  final int idEvento;
  final String nombre;
  final int capacidad;
  final double precio;

  ZonaDetalle({
    required this.id,
    required this.idEvento,
    required this.nombre,
    required this.capacidad,
    required this.precio,
  });
}

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const EventosPage(),
    );
  }
}
