import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

package object config {

  implicit val appConfigDecoder: Decoder[AppConfig] = deriveDecoder

}
