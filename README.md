# Belkacar custom PIP for WSO2 IS

## Setup instructions

__Build__

1. Go to <PIP_HOME>
2. Build project via 
``
mvn package
``

__Copy required binaries__

3. Copy the created ``target/com.belkacar.wso.pip-1.0.0.jar`` to the ``<IS_HOME>/repository/components/lib`` directory.
4. Copy any dependency libraries for the PIP module to ``<IS_HOME>/repository/components/lib directory``. This includes
    - ``lib/converter-gson-2.1.0.jar``
    - ``lib/okhttp-3.3.0.jar``
    - ``lib/okio-1.8.0.jar``
    - ``lib/retrofit-2.1.0.jar``

__Configure__
5. Edit ``configs/repository/conf/identity/entitlement.properties`` file
    - find line ``com.belkacar.wso.pip.RestAttributeFinderModule.1=Endpoint,http://abac-dev.belkacar.ru:8080/api/1.0/``
    - replace value _``http://abac-dev.belkacar.ru:8080/api/1.0/``_ with actual environment-dependant value
    - copy ``configs/repository/conf/identity/entitlement.properties`` to the ``<IS_HOME>/repository/conf/identity`` directory

Restart the server if it has been started already.

## Testing instructions
Follow the [WSO instructions](https://docs.wso2.com/display/IS530/Writing+a+Custom+Policy+Info+Point).
Test policy is ``./policy.xml``
