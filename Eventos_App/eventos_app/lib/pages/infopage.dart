import 'package:flutter/material.dart';

class InfoPage extends StatelessWidget {
  const InfoPage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Información'),
      ),
      body: ListView(
        padding: const EdgeInsets.all(8.0),
        children: [
          const SizedBox(height: 16.0),
          const Text(
            'Play Events Do es tu fuente de información confiable para eventos en República Dominicana. Aquí encontrarás detalles importantes sobre los eventos más destacados en el país. ¡No te pierdas la diversión y la emoción que ofrece la escena de eventos dominicana!',
            style: TextStyle(fontSize: 16.0),
          ),
          const SizedBox(height: 24.0),
          const Text(
            'Tipo de eventos',
            style: TextStyle(fontSize: 20.0, fontWeight: FontWeight.bold),
          ),
          const SizedBox(height: 16.0),
          const Text(
            'En República Dominicana podrás disfrutar de una amplia variedad de eventos emocionantes. Algunos de los tipos de eventos más populares incluyen:',
            style: TextStyle(fontSize: 16.0),
          ),
          const SizedBox(height: 8.0),
          const Text(
            '- Conciertos: Disfruta de presentaciones en vivo de tus artistas favoritos en los mejores escenarios del país.',
            style: TextStyle(fontSize: 16.0),
          ),
          const Text(
            '- Festivales: Sumérgete en la cultura dominicana a través de festivales temáticos que celebran la música, la gastronomía, el arte y más.',
            style: TextStyle(fontSize: 16.0),
          ),
          const Text(
            '- Deportes: Vive la pasión por el deporte en eventos deportivos de renombre, desde partidos de béisbol hasta competencias de surf.',
            style: TextStyle(fontSize: 16.0),
          ),
          const Text(
            '- Conferencias y seminarios: Amplía tus conocimientos y conecta con expertos en diversas áreas a través de conferencias y seminarios.',
            style: TextStyle(fontSize: 16.0),
          ),
          const Text(
            '- Exposiciones y ferias: Explora exhibiciones y ferias que presentan lo mejor de la industria, el arte, la tecnología y mucho más.',
            style: TextStyle(fontSize: 16.0),
          ),
          const Text(
            '- Eventos culturales: Sumérgete en la rica cultura dominicana a través de eventos folclóricos, tradicionales y festividades locales.',
            style: TextStyle(fontSize: 16.0),
          ),
          const SizedBox(height: 24.0),
          const Text(
            'Calendario de eventos',
            style: TextStyle(fontSize: 20.0, fontWeight: FontWeight.bold),
          ),
          const SizedBox(height: 16.0),
          const Text(
            'Mantente actualizado con nuestro calendario de eventos, donde encontrarás fechas, horarios y detalles de los próximos eventos en República Dominicana. ¡No te pierdas ninguna oportunidad de diversión!',
            style: TextStyle(fontSize: 16.0),
          ),
          const SizedBox(height: 24.0),
          const Text(
            'Compra de boletos',
            style: TextStyle(fontSize: 20.0, fontWeight: FontWeight.bold),
          ),
          const SizedBox(height: 16.0),
          const Text(
            'Para asistir a los eventos, puedes adquirir tus boletos a través de nuestro sitio web. Te ofrecemos una forma rápida y segura de obtener tus entradas para que no te quedes sin disfrutar de tus eventos favoritos.',
            style: TextStyle(fontSize: 16.0),
          ),
          const SizedBox(height: 24.0),
          const Text(
            'Contacto',
            style: TextStyle(fontSize: 20.0, fontWeight: FontWeight.bold),
          ),
          const SizedBox(height: 16.0),
          const Text(
            'Si tienes alguna pregunta o necesitas más información, no dudes en contactarnos. Estamos aquí para ayudarte y brindarte la mejor experiencia en la planificación de tus eventos en República Dominicana.',
            style: TextStyle(fontSize: 16.0),
          ),
        ],
      ),
    );
  }
}
