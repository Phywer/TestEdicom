import { Ubication } from "../../ubication/ubication";
import { UbicationService } from "../../ubication/ubication.service";
import { limpiarEstado, obtenerServicioUbicaciones } from "../comun";


describe('H07: Como usuario quiero activar una ubicación disponible en el sistema.', () => {

  let ubicacion: UbicationService;

  beforeEach(() => {
    ubicacion = obtenerServicioUbicaciones();
  });

  it('debería activar una ubicación', async () => {

    // Given: mi ubicación validada
    let miUbicacion: Ubication;
    miUbicacion = { name: 'Castelló de la Plana',
      lat: 39.986,
      lon: -0.0376709,
      country: 'ES'
    };

    // When: Intentamos activar la ubicación
    expect(ubicacion.activarUbicacion(miUbicacion))
      .toBeTrue(); // Then: Se activa la ubicación
  });

  it('la ubicación ya está activa', async () => {

    // Given: mi ubicación validada
    let miUbicacion: Ubication;
    miUbicacion = { name: 'Castelló de la Plana',
                    lat: 39.986,
                    lon: -0.0376709,
                    country: 'ES'
    };

    // When: Intentamos activar la ubicación
    expect(ubicacion.activarUbicacion(miUbicacion))
      .toBeFalse(); // Then: La ubicación no se puede activar
  });

  afterEach(() => {
    limpiarEstado();
  });
});
