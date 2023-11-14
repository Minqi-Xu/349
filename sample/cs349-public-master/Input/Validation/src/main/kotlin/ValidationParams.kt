
// form validation parameters
interface ValidationParams {
    val promptMessage: String
    val errorMessage: String
    val validationRegex: Regex
    val filterRegex: Regex
}

// example classes with parameters for specific types of form validation

data class PhoneNumberValidation(
    override val promptMessage: String =
        "Enter a North American phone number",
    override val errorMessage: String =
        "Expecting 10 digits (spacing, brackets, hyphen optional)",
    // based on re from https://stackoverflow.com/questions/16699007/
    override val validationRegex: Regex =
        Regex("""^\(?\d{3}\)?[\s.-]?\d{3}[\s.-]?\d{4}$"""),
    override val filterRegex: Regex =
            Regex("""^[\d\s()-]*"""),
) : ValidationParams

data class PostalCodeValidation(
    override val promptMessage: String =
        "Enter a Canadian postal code",
    override val errorMessage: String =
        "Expecting 6 characters like A1B 2C3 (spacing or hyphen optional)",
    // based on re from https://stackoverflow.com/questions/15774555
    override val validationRegex: Regex =
        Regex("""^[a-zA-Z]\d[a-zA-Z]\s*-?\s*\d[a-zA-Z]\d$"""),
    override val filterRegex: Regex =
        Regex("""^[\s\w-]*"""),
) : ValidationParams


data class NumericValidation(
    override val promptMessage: String =
        "Enter a number",
    override val errorMessage: String =
        "Expecting a decimal number",
    override val validationRegex: Regex =
        Regex("""^\d*\.?\d*$"""),
    override val filterRegex: Regex =
        Regex("""^(\d*\.?\d*)|\s$"""),
) : ValidationParams

