def call(String buildStatus = 'SUCCESS') {

    buildStatus = buildStatus ?: 'SUCCESS'

    def subject = ""
    def color = ""
    def message = ""

    if (buildStatus == 'SUCCESS') {
        subject = "✅ SUCCESS: ${env.JOB_NAME} #${env.BUILD_NUMBER}"
        color = "#28a745"
        message = "The Jenkins pipeline completed successfully."
    } else if (buildStatus == 'FAILURE') {
        subject = "❌ FAILURE: ${env.JOB_NAME} #${env.BUILD_NUMBER}"
        color = "#dc3545"
        message = "The pipeline failed. Please check the Jenkins logs for details."
    } else if (buildStatus == 'ABORTED') {
        subject = "⚠️ ABORTED: ${env.JOB_NAME} #${env.BUILD_NUMBER}"
        color = "#ffc107"
        message = "The Jenkins pipeline was aborted manually."
    } else {
        subject = "ℹ️ ${buildStatus}: ${env.JOB_NAME} #${env.BUILD_NUMBER}"
        color = "#17a2b8"
        message = "Pipeline finished with status: ${buildStatus}."
    }

    def body = """
        <html>
        <body style="font-family:Arial, sans-serif;">
            <h2 style="color:${color};">${subject}</h2>
            <p>${message}</p>
            <ul>
                <li><b>Job:</b> ${env.JOB_NAME}</li>
                <li><b>Build Number:</b> ${env.BUILD_NUMBER}</li>
                <li><b>Triggered By:</b> ${env.BUILD_USER ?: 'Automated Trigger'}</li>
                <li><b>Build URL:</b> <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></li>
            </ul>
            <p>Regards,<br><b>Jenkins CI/CD System</b></p>
        </body>
        </html>
    """

    emailext(
        to: 'aslahea068@gmail.com',
        subject: subject,
        body: body,
        mimeType: 'text/html'
    )
}
