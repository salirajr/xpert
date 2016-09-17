var _initFullHeightWrapper = function() {
	if ($('.footer').hasClass('fixed-full')) {
		var revalidateWrapperHeight = function() {
			var wrapper = document.getElementById('wrapper');
			var currentStyle = wrapper.currentStyle
					|| window.getComputedStyle(wrapper);

			wrapper.style.minHeight = (window.innerHeight - parseInt(currentStyle.paddingTop))
					+ 'px';
		}

		revalidateWrapperHeight();
		window.addEventListener('resize', revalidateWrapperHeight, false);
	}
	;
};

/**
 * Toggle the navbar visibility on page scroll.
 */
var _initHiddenNavbarOnScroll = function() {
	var navbar = document.getElementById('main-top-nav');
	var lastScrollTop = 0;

	document
			.addEventListener(
					'scroll',
					function() {
						var scrollTop = window.scrollY, isScrolledDown = (scrollTop > lastScrollTop);

						if (isScrolledDown) {
							navbar.style.top = '-32px';
						} else {
							navbar.removeAttribute('style');
						}

						lastScrollTop = scrollTop;
					});
};

_initFullHeightWrapper();
_initHiddenNavbarOnScroll();

toastr.options = {
	"closeButton" : true,
	"debug" : false,
	"progressBar" : true,
	"positionClass" : "toast-bottom-right",
	"onclick" : null,
	"showDuration" : "400",
	"hideDuration" : "1000",
	"timeOut" : "7000",
	"extendedTimeOut" : "1000",
	"showEasing" : "swing",
	"hideEasing" : "linear",
	"showMethod" : "fadeIn",
	"hideMethod" : "fadeOut"
}
/**
 * MODULE/*REFERRAL/*CONTAINER/*CONTAINER_ACTION/*ACTION_PARAMETER/*INSTANCE_NAME/*COMMAND/*PARAMETERS
 */
function fnGetControls(module, referral, container, action, aparameter,
		instance, command, cparameters) {

	var result = "";
	if (module != '')
		result += module + "/*";
	else
		result += "EVM/*";

	if (referral != '')
		result += referral + "/*";
	else
		result += "EVR/*";

	if (container != '')
		result += container + "/*";
	else
		result += "EVC/*";

	if (action != '')
		result += action + "/*";
	else
		result += "EVA/*";

	if (aparameter != '')
		result += aparameter + "/*";
	else
		result += "EVAP/*";

	if (instance != '')
		result += instance + "/*";
	else
		result += "EVI/*";

	if (command != '')
		result += command + "/*";
	else
		result += "EVCD/*";

	if (cparameters != '')
		result += cparameters;
	else
		result += "EVCDP";

	return result;
}