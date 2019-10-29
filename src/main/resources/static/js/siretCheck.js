 $(document).ready(function() {
    $('#siren').on('keyup', function(e) {
 
     if($('#siren').val() != '') {
    	 
               $('#siretValidation').removeClass().addClass('alert alert-warning').html('Le SIRET doit faire 12 chiffres');
 
            return false;
           }
           
        // Doit avoir une majuscule, des chiffres et des minuscules
        var validSiretSize = new RegExp("[0-9]{12}", "g");

         
        if (validSiretSize.test($(this).val)===false) {
            // Test si le Siret fait au moins 12 caractères
               $('#"siretValidation"').removeClass().addClass('alert alert-danger').html('Le SIRET doit faire 12 chiffres');
 
        } else if (strongRegex.test($(this).val())) {
            // If reg ex matches strong password
            $('#"siretValidation"').removeClass().addClass('alert alert-success').html('SIRET Ok!');
        } else if (mediumRegex.test($(this).val())) {
            // If medium password matches the reg ex
            $('#"siretValidation"').removeClass().addClass('alert alert-info').html('Votre mot de passe n\'est pas assez fort!');
        } else {
            // If password is ok
            $('#"siretValidation"').removeClass().addClass('alert alert-warning').html('Mot de passe faible, essayez d’utiliser des chiffres et des lettres majuscules.');
        }
 
        return true;
    })})