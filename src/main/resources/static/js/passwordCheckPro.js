 $(document).ready(function() {
	 $(".tohide").hide();
     $('#passwordInput, #confirmPasswordInput').on('keyup', function(e) {
 
     if($('#passwordInput').val() != '' && $('#confirmPasswordInput').val() != '' && $('#passwordInput').val() != $('#confirmPasswordInput').val())
            {
               $('#passwordStrength').removeClass().addClass('alert alert-warning').html('Les mots de passe ne correspondent pas!');
 
            return false;
           }
           
     	
        // Doit avoir une majuscule, des chiffres et des minuscules
        var strongRegex = new RegExp("^(?=.{8,})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*\\W).*$", "g");
 
        // Doit avoir des majuscules et des lettres minuscules ou des minuscules et des chiffres
        var mediumRegex = new RegExp("^(?=.{7,})(((?=.*[A-Z])(?=.*[a-z]))|((?=.*[A-Z])(?=.*[0-9]))|((?=.*[a-z])(?=.*[0-9]))).*$", "g");
 
        // Doit comporter au moins 6 caractères
        var okRegex = new RegExp("(?=.{6,}).*", "g");
 
        if (okRegex.test($(this).val()) === false && $('#passwordInput').val()!=$('#confirmPasswordInput').val()) {
            // If ok regex doesn't match the password
               $('#passwordStrength').removeClass().addClass('alert alert-warning').html('Le mot de passe doit faire au moins 6 caractères.');
               $(".tohide").hide();
               
        } else if (strongRegex.test($(this).val()) && $('#passwordInput').val()==$('#confirmPasswordInput').val()) {
            // If reg ex matches strong password
            $('#passwordStrength').removeClass().addClass('alert alert-success').html('Mot de passe Ok!');
            $(".tohide").show();
            
        } else if (mediumRegex.test($(this).val()) && $('#passwordInput').val()!=$('#confirmPasswordInput').val()) {
            // If medium password matches the reg ex
            $('#passwordStrength').removeClass().addClass('alert alert-danger').html('Votre mot de passe n\'est pas assez fort!');
            $(".tohide").hide();
            
        } else {
            // If password is ok
            $('#passwordStrength').removeClass().addClass('alert alert-warning').html('Mot de passe faible, essayez d’utiliser des chiffres et des lettres majuscules.');
            $(".tohide").hide();
        }
 
        return true;
    })})
    

    
    
    
    
    