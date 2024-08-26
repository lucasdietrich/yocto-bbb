pipeline {
    agent {
        docker { 
            image 'devops/fedora-yocto-toolchain:latest'
        }
    }

    options {
        disableConcurrentBuilds()
    }

    environment {
        WORKSPACE_BUILD_DIR = "${env.WORKSPACE}/build"
        WORKSPACE_DELIVERY_DIR = "${env.WORKSPACE}/delivery"

        YOCTO_TMP_DIR = "${env.WORKSPACE_BUILD_DIR}/tmp"
        YOCTO_DEPLOY_DIR = "${env.YOCTO_TMP_DIR}/deploy"

        YOCTO_DOWNLOAD_DIR = "${env.WORKSPACE_BUILD_DIR}/downloads"
        YOCTO_SSTATE_DIR = "${env.WORKSPACE_BUILD_DIR}/sstate-cache"
    }

    parameters {
        choice(
            name: 'IMAGE',
            choices: [
                'bbb-image-minimal',
            ],
            description: 'Image to build',
        )
        choice(
            name: 'MACHINE',
            choices: ['beaglebone'],
            description: 'Machine to build for',
        )
        choice(
            name: 'DISTRO',
            choices: ['poky'],
            description: 'Distro to build for',
        )
    }

    stages {
        stage('Init') {
            steps {
                script {
                    buildNumber = "${params.IMAGE}-${params.MACHINE}-${params.DISTRO}-${BUILD_NUMBER}"
                }
            }
        }

        stage('Git checkout submodules') {
            steps {
                sh 'git submodule update --init --recursive'
            }
        }

        stage('Create environment') {
            steps {
                withEnv(["HOME=${env.WORKSPACE}"]) {
                    sh 'python3 -m venv .venv'
                    sh 'source .venv/bin/activate'
                    sh 'pip install --upgrade pip'
                    sh 'python3 -m pip install pycryptodome pyelftools jinja2 pexpect GitPython cryptography'
                    sh 'source poky/oe-init-build-env $WORKSPACE_BUILD_DIR'
                }
            }
        }

        stage('Configure yocto project') {
            steps {
                // append to local.conf
                sh """
                    echo '' >> ${env.WORKSPACE_BUILD_DIR}/conf/local.conf
                    echo 'DL_DIR = "${env.YOCTO_DOWNLOAD_DIR}"' >> ${env.WORKSPACE_BUILD_DIR}/conf/local.conf
                    echo 'SSTATE_DIR = "${env.YOCTO_SSTATE_DIR}"' >> ${env.WORKSPACE_BUILD_DIR}/conf/local.conf
                    echo 'MACHINE = "${params.MACHINE}"' >> ${env.WORKSPACE_BUILD_DIR}/conf/local.conf
                    echo 'DISTRO = "${params.DISTRO}"' >> ${env.WORKSPACE_BUILD_DIR}/conf/local.conf
                """
            }
        }

        stage('Debug') {
            steps {
                sh "printenv"
                sh "locale"
                sh "cat ${env.WORKSPACE_BUILD_DIR}/conf/local.conf"
                sh "cat ${env.WORKSPACE_BUILD_DIR}/conf/bblayers.conf"
            }
        }

        stage('Build image') {
            steps {
                sh """
                    source .venv/bin/activate
                    source poky/oe-init-build-env ${env.WORKSPACE_BUILD_DIR}
                    bitbake ${params.IMAGE}
                """
            }
        }

        stage('Export images') {
            steps {
                dir("${env.YOCTO_DEPLOY_DIR}") {
                    script {
                        def symlinkFiles = [
                            "images/${params.MACHINE}/${params.IMAGE}-${params.MACHINE}.rootfs.manifest",
                            "images/${params.MACHINE}/${params.IMAGE}-${params.MACHINE}.rootfs.wic.bz2",
                            "images/${params.MACHINE}/${params.IMAGE}-${params.MACHINE}.rootfs.tar.bz2",
                        ]

                        symlinkFiles.each { file ->
                            def resolvedFile = sh(script: "readlink -f ${file}", returnStdout: true).trim()
                            
                            if (resolvedFile != "${env.YOCTO_DEPLOY_DIR}/${file}") {
                                sh "cp --remove-destination ${resolvedFile} ${file}"
                            }

                            archiveArtifacts artifacts: "${file}", fingerprint: true
                        }

                        archiveArtifacts artifacts: "licenses/${params.MACHINE}/${params.IMAGE}-${params.MACHINE}.rootfs/*", fingerprint: true
                    }
                }
            }
        }

        stage('Build SDK') {
            steps {
                sh """
                    source .venv/bin/activate
                    source poky/oe-init-build-env ${env.WORKSPACE_BUILD_DIR}
                    bitbake -c populate_sdk ${params.IMAGE}
                """
            }
        }

        stage('Export SDK') {
            steps {
                dir("${env.YOCTO_DEPLOY_DIR}") {
                    script {
                        // make .sh file executable
                        sh "chmod +x sdk/*.sh"

                        archiveArtifacts artifacts: "sdk/*", fingerprint: true
                    }
                }
            }
        }
    }
    post {
        always {
            dir("$YOCTO_TMP_DIR") {
                deleteDir()
            }
        }
    }
}
