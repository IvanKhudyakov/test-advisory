node {
	stage ('SCM checkout'){
		git "https://github.com/ivankhudyakov/test-advisory"
		}
	stage ('Build'){
    		dir("test-advisory") {
		   	sh "mvn clean install"
       		}
       		dir("test-advisory/target") {
	   		sh "java -jar test-advisory-1.0-SNAPSHOT.jar"
       		}
	}
}
