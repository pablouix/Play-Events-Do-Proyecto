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
                    hora = table.Column<string>(type: "TEXT", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Eventos", x => x.id);
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

            migrationBuilder.CreateIndex(
                name: "IX_ZonasDetalles_idEvento",
                table: "ZonasDetalles",
                column: "idEvento");
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "ZonasDetalles");

            migrationBuilder.DropTable(
                name: "Eventos");
        }
    }
}
