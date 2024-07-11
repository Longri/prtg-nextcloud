<?php

namespace OCA\PRTGNextcloud\Controller;

use OCP\IRequest;
use OCP\AppFramework\Http\TextPlainResponse;
use OCP\AppFramework\Controller;
use OC;

class ApiController extends Controller {

    public function __construct($AppName, IRequest $request) {
        parent::__construct($AppName, $request);
    }



    /**
     * @NoAdminRequired
     * @PublicPage
     * @NoCSRFRequired
     */
    public function query($parameter) {


	// Dekodieren des URI-kodierten Parameters
        $decodedParameter = urldecode($parameter);


	//Ensure that the output is UTF-8 encoded
	$locale='de_DE.UTF-8';
	setlocale(LC_ALL,$locale);
	putenv('LC_ALL='.$locale);

        // Determine the path to the Nextcloud root directory
        $ncRoot = OC::$SERVERROOT;

        // Path to the occ file relative to the Nextcloud root directory
        $occPath = $ncRoot . '/occ';

        // Command for executing the OCC command
        $occCommand = 'php ' . escapeshellarg($occPath) . ' usage-report:generate --display-name 2>&1';

        // Execute the OCC command and record the output
        $occOutput = shell_exec($occCommand);

        // Check whether an output is available
        if ($occOutput === null) {
            $occOutput = 'Error executing the OCC command. Please check the path and the authorisations.';
        }

        // Path to the JAR file relative to the Nextcloud root directory
        $jarPath = $ncRoot . '/apps/prtgnextcloud/PRTG-Nextcloud.jar';

        // Command for executing the Java application with transfer of the OCC output
        $javaCommand = 'java -Dfile.encoding=UTF-8 -cp ' . escapeshellarg($jarPath) . ' de.longri.prtg.nextcloud.Main ' . escapeshellarg($occOutput) . ' ' .  $decodedParameter . ' 2>&1';
//	$javaCommand = 'java -Dfile.encoding=UTF-8 -cp ' . escapeshellarg($jarPath) . ' de.longri.prtg.nextcloud.Main ' . escapeshellarg($occOutput) . ' ' . '-d=Disk1:root' . ' 2>&1';


        // Executing the Java command and capturing the output
        $javaOutput = shell_exec($javaCommand);

        // Check whether an output is available
        if ($javaOutput === null) {
            $javaOutput = 'Error executing the Java command. Please check the path and the authorisations.';
        }


        // Return of the response as XML text
        $response = new TextPlainResponse($javaOutput);
	// DEBUG OUTPUT $response = new TextPlainResponse($occOutput);

        $response->addHeader('Content-Type', 'application/xml; charset=UTF-8');

        return $response;
    }
}
