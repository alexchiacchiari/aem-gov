(function (document, $,  ns){
    "use strict";
    $(document).on("click", ".cq-dialog-submit", function (e) {

        e.stopPropagation();
        e.preventDefault();
        var $form = $(this).closest("form.foundation-form"), $inputs = $form.find("[name$='./name']"),
        $input=null,
        textValue,
        isError=false,
        patterns = {
            regex: /[0-9]/
        };
        $inputs.each(function(index, input) {
            $input = $(input);
            textValue=$input.val();
            if(textValue != "" && patterns.regex.test(textValue) && (textValue != null)) {
                isError=true;
                $input.css("border", "2px solid #FF0000");
                ns.ui.helpers.prompt({
                    title: Granite.I18n.get("Invalid Name"),
                    message: "Please insert a name without numbers",
                    actions: [{
                        id: "CANCEL",
                        text: "CANCEL",
                        className: "coral-Button"
                    }],
                    callback: function (actionId) {
                        return actionId === "CANCEL";
                    }
                });
            } else {
                $input.css("border", "");
            }
    });
 
    if(!isError){
        $form.submit();
    } 
 });
})
    
   /*  const DIALOG_RESOURCE_TYPE = "gov/components/saveondb";

    function isTargetDialog($formElement, dialogResourceType) {
        const resourceType = $formElement.find("input[name='./sling:resourceType']").val();
        return resourceType === dialogResourceType;
    }

    $(document).on("dialog-ready", function() {
        const $formElement = $(this).find('coral-dialog form.foundation-form');
        if (!isTargetDialog($formElement, DIALOG_RESOURCE_TYPE)) {
            return;
        } else {
            $("button.cq-dialog-header-action:last").click(() => {
                let $val = $("coral-multifield-item .coral-Form-fieldwrapper")
                                .find("input[data-validation='name']")
                                .val();
                if($val.length > 10) {
                    
                }
            })
        }
    }); 
    }(document, Granite.$, Granite.author));


    register antonio:

    ——————-

(function (window, $, Granite, Coral) {

	"use strict";



	var ACN_TEXT_MAX_LENGTH_4 = "acn-text-max-length-4";

    	registry.register("foundation.validation.validator", {

		selector: "[data-foundation-validation~='" + NUMBER_LESS_THAN_24 + "']",

        
    
—————-

	var registry = $(window).adaptTo("foundation-registry");
    



—————-



		validate: function(element) {





			if (isNaN(element.value) ) {

				return "Only a number less than 24 is allowed";

			}

			if (element.value >= 24 ) {

				return "A value less than 24 is expected";

			}



		}

	})


})(window, Granite.$, Granite, Coral);


*
*/
