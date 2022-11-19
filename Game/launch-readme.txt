DyWA_* launch configurations use dywa-config/wildfly-starter/pom.xml
App_* launch configurations use target/dywa-app/pom.xml

DyWA_Clean: removes everything from wildfly deployment:  app.war / app-dywa-bridge / dywa.ear /  all uploaded files / database
DyWA_Deploy: Deploys the dywa.ear from maven repository into wildfly/standalone/deployments
DyWA_Start: copy standalone-embedded.xml / deploy dywa.ear / start wildfly 
DyWA_Stop: stop wildlfly

App_Clean: cleans target/dywa_app with maven clean command
App_Deploy: builds target/dywa_app with maven and installs resulting war file into wildfly/standalone/deployments
