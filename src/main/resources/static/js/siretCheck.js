 $(document).ready(function() {
    $('#siren').on('keyup', function(e) {
 
     if($('#siren').val() != '') {
    	 
               $('#siretValidation').removeClass().addClass('alert alert-warning').html('Le SIRET doit faire 12 chiffres');
 
            return false;
           }
           
        // Doit avoir une majuscule, des chiffres et des minuscules
        var validSiretSize = new RegExp("[0-9]{12}", "g");

         
        if (validSiretSize.test($(this).val)===false) {
            // Test si le Siret fait au moins 12 chiffres
               $('#"siretValidation"').removeClass().addClass('alert alert-danger').html('Le SIRET doit faire 12 chiffres');
 
        } else if (validSiretSize.test($(this).val)===true) {
            // If fait bien 12 chiffres
            $('#"siretValidation"').removeClass().addClass('alert alert-success').html('SIRET Ok!');
        } else {
            // FAire fonction test si les 9 premier chiffre sont un multiple de 10
            $('#"siretValidation"').removeClass().addClass('alert alert-warning').html('Mot de passe faible, essayez d’utiliser des chiffres et des lettres majuscules.');
        }
 
        return true;
    })})