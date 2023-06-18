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
		sh 'docker build -t diploma-webapp:latest .' 
                sh 'docker tag  diploma-webapp gabrielagherman/diploma-webapp:latest'
               
          }
        }
     
  stage('Publish image to Docker Hub') {
          
            steps {
        withDockerRegistry([ credentialsId: "docker-cred", url: "" ]) {
          sh  'docker push gabrielagherman/diploma-webapp:latest'
        }
                  
          }
        }
     
   
	 
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
