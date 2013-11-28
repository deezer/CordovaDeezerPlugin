

var exec = require('cordova/exec');

/**
 * Provides access to the vibration mechanism on the device.
 */

module.exports = {

    /**
     * Causes the device to vibrate.
     *
     * @param {Integer} mills       The number of milliseconds to vibrate for.
     */
    vibrate: function(mills) {
        /**
         * exec (
         *          successFunctionCallBack
         */
        
        exec(null, null, "Vibration", "vibrate", [mills]);
    },
};
