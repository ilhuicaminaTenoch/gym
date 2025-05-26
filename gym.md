classDiagram
direction BT
class AccesoController
class AccesoEntity
class AccesoRepository {
<<Interface>>

}
class AccesoRequest
class AccesoResponse
class AccesoService
class AjusteStockRequest
class AuthController
class AuthService
class CajaController
class CajaEntity
class CajaRepository {
<<Interface>>

}
class CajaRequest
class CajaResponse
class CajaSecuenciaEntity
class CajaSecuenciaId
class CajaSecuenciaRepository {
<<Interface>>

}
class CajaSecuenciaResponse
class CajaService
class ClienteController
class ClienteEntity
class ClienteFilterDto
class ClienteRepository {
<<Interface>>

}
class ClienteRequest
class ClienteResponse
class ClienteService
class ClienteSpecification
class ClienteUpdateRequest
class CorsConfig
class DetalleVentaEntity
class DetalleVentaRepository {
<<Interface>>

}
class DetalleVentaRequest
class DetalleVentaResponse
class DispositivoBiometricoEntity
class EntidadEditable
class Estado {
<<enumeration>>

}
class EstatusProducto {
<<enumeration>>

}
class FileUtils
class Genero {
<<enumeration>>

}
class GlobalExceptionHandler
class GymApplication
class GymApplicationTests
class IntentoAccesoFallidoEntity
class ItemAjusteStockRequest
class JwtAuthenticationFilter
class JwtUtil
class LogAccesoEntity
class LogAccesoRepository {
<<Interface>>

}
class LoginRequest
class MetodoAcceso {
<<enumeration>>

}
class MetodoPago {
<<enumeration>>

}
class PagoRequest
class PagoResponse
class PagoSuscripcionResponse
class PlanSuscripcionEntity
class PlanSuscripcionRepository {
<<Interface>>

}
class PlanSuscripcionRequest
class ProductoController
class ProductoEntity
class ProductoFilterDto
class ProductoRepository {
<<Interface>>

}
class ProductoRequest
class ProductoResponse
class ProductoService
class ProductoSpecification
class ProductoUpdateRequest
class PromocionController
class PromocionEntity
class PromocionFilterDto
class PromocionRepository {
<<Interface>>

}
class PromocionRequest
class PromocionResponse
class PromocionService
class PromocionSpecification
class Role {
<<enumeration>>

}
class SecurityConfig
class SuscripcionController
class SuscripcionEntity
class SuscripcionRepository {
<<Interface>>

}
class SuscripcionRequest
class SuscripcionResponse
class SuscripcionService
class TipoAcceso {
<<enumeration>>

}
class TipoPlan {
<<enumeration>>

}
class TipoVenta {
<<enumeration>>

}
class TokenType {
<<enumeration>>

}
class TurnoEntity
class TurnoRepository {
<<Interface>>

}
class UniqueCodigoBarras
class UniqueCodigoBarrasOnUpdate
class UniqueCodigoBarrasOnUpdateValidator
class UniqueCodigoBarrasValidator
class UniqueEmail
class UniqueEmailOnUpdate
class UniqueEmailOnUpdateValidator
class UniqueEmailValidator
class UsuarioController
class UsuarioDetalleController
class UsuarioDetalleEntity
class UsuarioDetalleRepository {
<<Interface>>

}
class UsuarioDetalleRequest
class UsuarioDetalleResponse
class UsuarioDetalleService
class UsuarioEntity
class UsuarioRepository {
<<Interface>>

}
class UsuarioRequest
class UsuarioResponse
class UsuarioService
class UsuarioTokenEntity
class UsuarioTokenRepository {
<<Interface>>

}
class VentaController
class VentaEntity
class VentaRepository {
<<Interface>>

}
class VentaRequest
class VentaResponse
class VentaService

AccesoEntity  -->  EntidadEditable 
CajaEntity  -->  EntidadEditable 
ClienteEntity  -->  EntidadEditable 
UniqueEmailOnUpdate  ..  ClienteUpdateRequest 
DetalleVentaEntity  -->  EntidadEditable 
DispositivoBiometricoEntity  -->  EntidadEditable 
IntentoAccesoFallidoEntity  -->  EntidadEditable 
LogAccesoEntity  -->  EntidadEditable 
ProductoEntity  -->  EntidadEditable 
UniqueCodigoBarrasOnUpdate  ..  ProductoUpdateRequest 
PromocionEntity  -->  EntidadEditable 
SuscripcionEntity  -->  EntidadEditable 
TurnoEntity  -->  EntidadEditable 
UsuarioDetalleEntity  -->  EntidadEditable 
UsuarioEntity  -->  EntidadEditable 
UsuarioTokenEntity  -->  EntidadEditable 
VentaEntity  -->  EntidadEditable 
