using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Eventos_Api.DATA;
using Eventos_Api.Models;

namespace Eventos_Api.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class BoletasController : ControllerBase
    {
        private readonly Context _context;

        public BoletasController(Context context)
        {
            _context = context;
        }

        // GET: api/Boletas
        [HttpGet]
        public async Task<ActionResult<IEnumerable<Boletas>>> GetBoletas()
        {
          if (_context.Boletas == null)
          {
              return NotFound();
          }
            return await _context.Boletas.ToListAsync();
        }

        // GET: api/Boletas/5
        [HttpGet("{id}")]
        public async Task<ActionResult<Boletas>> GetBoletas(int id)
        {
          if (_context.Boletas == null)
          {
              return NotFound();
          }
            var boletas = await _context.Boletas.FindAsync(id);

            if (boletas == null)
            {
                return NotFound();
            }

            return boletas;
        }

        // PUT: api/Boletas/5
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPut("{id}")]
        public async Task<IActionResult> PutBoletas(int id, Boletas boletas)
        {
            if (id != boletas.Id)
            {
                return BadRequest();
            }

            _context.Entry(boletas).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!BoletasExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return NoContent();
        }

        // POST: api/Boletas
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPost]
        public async Task<ActionResult<Boletas>> PostBoletas(Boletas boletas)
        {
          if (_context.Boletas == null)
          {
              return Problem("Entity set 'Context.Boletas'  is null.");
          }
            _context.Boletas.Add(boletas);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetBoletas", new { id = boletas.Id }, boletas);
        }

        // DELETE: api/Boletas/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteBoletas(int id)
        {
            if (_context.Boletas == null)
            {
                return NotFound();
            }
            var boletas = await _context.Boletas.FindAsync(id);
            if (boletas == null)
            {
                return NotFound();
            }

            _context.Boletas.Remove(boletas);
            await _context.SaveChangesAsync();

            return NoContent();
        }

        private bool BoletasExists(int id)
        {
            return (_context.Boletas?.Any(e => e.Id == id)).GetValueOrDefault();
        }
    }
}
