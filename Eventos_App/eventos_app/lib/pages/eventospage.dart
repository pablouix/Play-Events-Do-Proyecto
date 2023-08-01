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
              compradas: zonaData['compradas'],
              precio: zonaData['precio'].toDouble(),
              disponibles: zonaData['disponibles'],
            ),
          );
        }
        fetchedEventos.add(
          Evento(
            id: eventoData['id'],
            nombre: eventoData['nombre'],
            fecha: eventoData['fecha'],
            hora: eventoData['hora'],
            zonaDetalles: zonaDetalles,
          ),
        );
      }
      setState(() {
        eventos = fetchedEventos;
      });
    } else {
      // Mostrar el mensaje de error si la solicitud no fue exitosa
      showDialog(
        context: context,
        builder: (BuildContext context) {
          return AlertDialog(
            title: Text('Error'),
            content: Text('Error al obtener los eventos'),
            actions: <Widget>[
              TextButton(
                child: Text('Aceptar'),
                onPressed: () {
                  Navigator.of(context).pop();
                },
              ),
            ],
          );
        },
      );
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

  Future<void> comprarBoletas(ZonaDetalle zona, int cantidadBoletas) async {
    // Realizar la actualización en el servidor del API
    final response = await http.put(
      Uri.parse('http://eventos.somee.com/api/ZonasDetalles/${zona.id}'), // Corregido el endpoint de la API para comprar boletas
      headers: {
        'Content-Type': 'application/json',
      },
      body: jsonEncode({
        'compradas': zona.compradas + cantidadBoletas,
        'disponibles': zona.disponibles - cantidadBoletas,
      }),
    );

    if (response.statusCode == 200) {
      // Mostrar la página emergente de compra exitosa
      showDialog(
        context: context,
        builder: (BuildContext context) {
          return AlertDialog(
            title: Text('Compra Exitosa'),
            content: Text(
              '¡Boletas compradas! Zona: ${zona.nombre}, Boletas: $cantidadBoletas',
            ),
            actions: <Widget>[
              TextButton(
                child: Text('Aceptar'),
                onPressed: () {
                  Navigator.of(context).pop();
                },
              ),
            ],
          );
        },
      );
    } else {
      // Mostrar el mensaje de error si la solicitud de compra falla
      showDialog(
        context: context,
        builder: (BuildContext context) {
          return AlertDialog(
            title: Text('Error'),
            content: Text('Error al comprar boletas'),
            actions: <Widget>[
              TextButton(
                child: Text('Aceptar'),
                onPressed: () {
                  Navigator.of(context).pop();
                },
              ),
            ],
          );
        },
      );
    }
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
                subtitle: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text('Fecha: ${evento.fecha}'),
                    Text('Hora: ${evento.hora}'),
                  ],
                ),
                trailing: ElevatedButton(
                  onPressed: () {
                    toggleZone(index);
                  },
                  style: ButtonStyle(
                    backgroundColor: MaterialStateProperty.all<Color>(Colors.blue),
                  ),
                  child: Text(
                    expandedZones.contains(index) ? 'Ocultar Zonas' : 'Mostrar Zonas',
                    style: TextStyle(
                      color: Colors.white,
                    ),
                  ),
                ),
              ),
              if (expandedZones.contains(index))
                Column(
                  children: evento.zonaDetalles.map((zona) {
                    return ListTile(
                      title: Text(zona.nombre),
                      subtitle: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Text('Capacidad ${zona.capacidad} Personas, Precio ${zona.precio.toStringAsFixed(2)} DOP'),
                          Text('Boletas disponibles: ${zona.disponibles}'),
                        ],
                      ),
                      trailing: ElevatedButton(
                        onPressed: () {
                          showDialog(
                            context: context,
                            builder: (BuildContext context) {
                              int cantidadBoletas = 0;

                              return AlertDialog(
                                title: Text('Comprar Boletas'),
                                content: Column(
                                  mainAxisSize: MainAxisSize.min,
                                  children: [
                                    Text('Ingrese la cantidad:'),
                                    TextField(
                                      keyboardType: TextInputType.number,
                                      onChanged: (value) {
                                        cantidadBoletas = int.tryParse(value) ?? 0;
                                      },
                                    ),
                                  ],
                                ),
                                actions: <Widget>[
                                  TextButton(
                                    child: Text('Cancelar'),
                                    onPressed: () {
                                      Navigator.of(context).pop();
                                    },
                                  ),
                                  TextButton(
                                    child: Text('Comprar'),
                                    onPressed: () {
                                      if (cantidadBoletas > 0 && cantidadBoletas <= zona.disponibles) {
                                        comprarBoletas(zona, cantidadBoletas);
                                        Navigator.of(context).pop();
                                      } else {
                                        showDialog(
                                          context: context,
                                          builder: (BuildContext context) {
                                            return AlertDialog(
                                              title: Text('Error'),
                                              content: Text('Cantidad de boletas inválida'),
                                              actions: <Widget>[
                                                TextButton(
                                                  child: Text('Aceptar'),
                                                  onPressed: () {
                                                    Navigator.of(context).pop();
                                                  },
                                                ),
                                              ],
                                            );
                                          },
                                        );
                                      }
                                    },
                                  ),
                                ],
                              );
                            },
                          );
                        },
                        child: Text('Comprar'),
                      ),
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
  final String hora;
  final List<ZonaDetalle> zonaDetalles;

  Evento({
    required this.id,
    required this.nombre,
    required this.fecha,
    required this.hora,
    required this.zonaDetalles,
  });
}

class ZonaDetalle {
  final int id;
  final int idEvento;
  final String nombre;
  final int capacidad;
  final int compradas;
  final double precio;
  final int disponibles;

  ZonaDetalle({
    required this.id,
    required this.idEvento,
    required this.nombre,
    required this.capacidad,
    required this.compradas,
    required this.precio,
    required this.disponibles,
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
