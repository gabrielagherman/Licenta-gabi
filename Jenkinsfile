pipeline {
    agent any
	
	  tools
    {
       maven "Maven"
    }
 stages {
      stage('checkout') {
           steps {
             
                git branch: 'main', url: 'https://github.com/gabrielagherman/Licenta-gabi.git'
             
          }
        }
	 stage('Execute Maven') {
           steps {
             
                sh 'mvn package'             
          }
        }
        

  stage('Docker Build and Tag') {
           steps { 
	      //sh ' docker socket'              
	//sh 'sudo chmod 777 /var/run/docker.sock'
		sh 'docker build -t webapp-calc-test:latest .' 
                sh 'docker tag webapp-calc-test gabrielagherman/webapp-calc-test:latest'
               
          }
        }
     
  stage('Publish image to Docker Hub') {
          
            steps {
        withDockerRegistry([ credentialsId: "docker-cred", url: "" ]) {
          sh  'docker push gabrielagherman/webapp-calc-test:latest'
        }
                  
          }
        }
     
      // stage('Run Docker container on Jenkins Agent') {
             
          //   steps 
			//{
          //   sh "docker run -d -p 8003:8080 gabrielagherman/calculator"
 
           // }
        // }
 
	// stage("Git Checkout"){
	//	 steps{
	//		 sh 'pwd'
	//		 sh 'git clone https://github.com/gabrielagherman/calculator-servlet-example.git'
	//	 	 sh 'pwd'
	//	 }
	 //}
	 
	 
	stage("Git Checkout") {
 	 steps {
   		script {
    	  		def directory = "Licenta-gabi"
      			if (!fileExists(directory)) {
       		 		sh 'git clone https://github.com/gabrielagherman/Licenta-gabi.git'
    	  		} else {
				echo "Repository already cloned"
      }
    }
  }
}

	 
	// stage ("Run ansible playbook on remote hosts")
	// {
	//	 steps{
	//		sh 'cd /etc/ansible'
	//		sh 'pwd' 
	//		sh 'ansible-playbook ./calculator-servlet-example/playbook.yaml -i ./calculator-servlet-example/inventory --key-file ./calculator-servlet-example/aws-key.pem'
	//	 }
	//}
	 
	 
	 stage("Run ansible playbook on remote hosts") {
	  steps {
	    script {
	      sh 'cd /etc/ansible'
	      sh 'pwd'
	      sh 'chmod 400 Licenta-gabi/aws-key.pem'
	      def playbook_status = sh(returnStatus: true, script: 'ansible-playbook ./Licenta-gabi/playbook.yaml -i ./Licenta-gabi/inventory --key-file ./Licenta-gabi/aws-key.pem')
	      if (playbook_status == 0) {
		echo "Playbook executed successfully"
	      } else {
		error "Failed to execute playbook"
	      }
	    }
	  }
	}


	}
}
