<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" indent="yes" />

<xsl:template match="/">
   <html>
   <p><b>Video Consultation's information</b></p>
   <p><br><b>Type of call: </b><xsl:value-of select="//type_of_call"/></br>
  <br><b>Date and time of the appointment: </b>
   <ht><xsl:value-of select="//consultation_date"/> at <xsl:value-of select="//consultation_time"/></ht></br>
   <br><b>Doctor's information:</b></br>
   <br> - Name: <xsl:value-of select="//doc/@name"/></br>
   <br> - ID: <xsl:value-of select="//id"/></br>
   <br> - Specialization: <xsl:value-of select="//specialization"/></br>
   <br> - Hospital: <xsl:value-of select="//hospital"/></br></p>
   <p><b>Prescriptions:</b></p>
   <table border="1">
   <th>Drug/Treatment</th>
   <th>Doses</th>
   <th>Duration</th>
   <th>notes</th>
   <xsl:for-each select="//prescriptions"/>
   <tr>
            <td><i><xsl:value-of select="//prescription/name" /></i></td>
            <td><xsl:value-of select="//prescription/doses" /></td>
            <td><xsl:value-of select="//prescription/@duration" /></td>
            <td><xsl:value-of select="//prescription/@notes" /></td>
   </tr>
   </table>
   </html>
</xsl:template>

</xsl:stylesheet>