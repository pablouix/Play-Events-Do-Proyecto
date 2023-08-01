using System;
using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace Eventos_Api.Migrations
{
    /// <inheritdoc />
    public partial class migracion_inicial : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "Eventos",
                columns: table => new
                {
                    id = table.Column<int>(type: "INTEGER", nullable: false)
                        .Annotation("Sqlite:Autoincrement", true),
                    imagen = table.Column<string>(type: "TEXT", nullable: false),
                    nombre = table.Column<string>(type: "TEXT", nullable: false),
                    fecha = table.Column<string>(type: "TEXT", nullable: false),
                    hora = table.Column<string>(type: "TEXT", nullable: false),
                    Descripcion = table.Column<string>(type: "TEXT", nullable: false),
                    Lugar = table.Column<string>(type: "TEXT", nullable: false),
                    Organizador = table.Column<string>(type: "TEXT", nullable: false),
                    EsPublico = table.Column<bool>(type: "INTEGER", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Eventos", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "Usuarios",
                columns: table => new
                {
                    Id = table.Column<int>(type: "INTEGER", nullable: false)
                        .Annotation("Sqlite:Autoincrement", true),
                    Nombre = table.Column<string>(type: "TEXT", nullable: false),
                    Contrasena = table.Column<string>(type: "TEXT", nullable: false),
                    Email = table.Column<string>(type: "TEXT", nullable: false),
                    Telefono = table.Column<string>(type: "TEXT", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Usuarios", x => x.Id);
                });

            migrationBuilder.CreateTable(
                name: "ZonasDetalles",
                columns: table => new
                {
                    id = table.Column<int>(type: "INTEGER", nullable: false)
                        .Annotation("Sqlite:Autoincrement", true),
                    idEvento = table.Column<int>(type: "INTEGER", nullable: false),
                    nombre = table.Column<string>(type: "TEXT", nullable: false),
                    capacidad = table.Column<int>(type: "INTEGER", nullable: false),
                    compradas = table.Column<int>(type: "INTEGER", nullable: false),
                    precio = table.Column<double>(type: "REAL", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_ZonasDetalles", x => x.id);
                    table.ForeignKey(
                        name: "FK_ZonasDetalles_Eventos_idEvento",
                        column: x => x.idEvento,
                        principalTable: "Eventos",
                        principalColumn: "id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "Boletas",
                columns: table => new
                {
                    Id = table.Column<int>(type: "INTEGER", nullable: false)
                        .Annotation("Sqlite:Autoincrement", true),
                    IdZonaDetalle = table.Column<int>(type: "INTEGER", nullable: false),
                    IdUsuario = table.Column<int>(type: "INTEGER", nullable: false),
                    FechaCompra = table.Column<DateTime>(type: "TEXT", nullable: false),
                    NumeroAsiento = table.Column<string>(type: "TEXT", nullable: false),
                    Precio = table.Column<double>(type: "REAL", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Boletas", x => x.Id);
                    table.ForeignKey(
                        name: "FK_Boletas_Usuarios_IdUsuario",
                        column: x => x.IdUsuario,
                        principalTable: "Usuarios",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_Boletas_ZonasDetalles_IdZonaDetalle",
                        column: x => x.IdZonaDetalle,
                        principalTable: "ZonasDetalles",
                        principalColumn: "id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateIndex(
                name: "IX_Boletas_IdUsuario",
                table: "Boletas",
                column: "IdUsuario");

            migrationBuilder.CreateIndex(
                name: "IX_Boletas_IdZonaDetalle",
                table: "Boletas",
                column: "IdZonaDetalle");

            migrationBuilder.CreateIndex(
                name: "IX_ZonasDetalles_idEvento",
                table: "ZonasDetalles",
                column: "idEvento");
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "Boletas");

            migrationBuilder.DropTable(
                name: "Usuarios");

            migrationBuilder.DropTable(
                name: "ZonasDetalles");

            migrationBuilder.DropTable(
                name: "Eventos");
        }
    }
}
