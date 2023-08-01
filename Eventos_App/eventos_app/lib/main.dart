import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:eventos_app/menu.dart';

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
      home: const LoginPage(),
      routes: {
        '/menu': (context) => const MenuPage(),
      },
    );
  }
}

class LoginPage extends StatefulWidget {
  const LoginPage({Key? key}) : super(key: key);

  @override
  _LoginPageState createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  TextEditingController _usernameController = TextEditingController();
  TextEditingController _passwordController = TextEditingController();

  Future<bool> _authenticateUser(String username, String password) async {
    final url = Uri.parse('http://eventos.somee.com/api/Usuarios');
    final response = await http.get(url);

    if (response.statusCode == 200) {
      final List<dynamic> users = jsonDecode(response.body);

      for (final user in users) {
        if (user['nombre'] == username && user['contrasena'] == password) {
          return true;
        }
      }
    }

    return false;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Play Events Do'),
      ),
      body: Center(
        child: Padding(
          padding: const EdgeInsets.all(20.0),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              TextField(
                controller: _usernameController,
                decoration: const InputDecoration(
                  labelText: 'Username',
                ),
              ),
              TextField(
                controller: _passwordController,
                decoration: const InputDecoration(
                  labelText: 'Password',
                ),
                obscureText: true,
              ),
              const SizedBox(height: 20.0),
              ElevatedButton(
                onPressed: () async {
                  String username = _usernameController.text;
                  String password = _passwordController.text;

                  bool isAuthenticated = await _authenticateUser(username, password);

                  if (isAuthenticated) {
                    Navigator.pushNamed(context, '/menu');
                  } else {
                    showDialog(
                      context: context,
                      builder: (context) => AlertDialog(
                        title: const Text('Error de autenticación'),
                        content: const Text('Credenciales inválidas.'),
                        actions: [
                          TextButton(
                            onPressed: () {
                              Navigator.pop(context);
                            },
                            child: const Text('Aceptar'),
                          ),
                        ],
                      ),
                    );
                  }
                },
                child: const Text('Iniciar sesión'),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
