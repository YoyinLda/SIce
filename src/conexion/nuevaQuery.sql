SELECT DISTINCT(CI.FEC_MEDIDA)
  ,S.DESC_SECTOR
--  ,S.COD_SECTOR
  ,CI.COD_TRAMO
  ,TE.IDENT
  ,CI.IND_TIPO
  ,DECODE(CI.IND_SENTIDO, '-','Norte-Sur', '+','Sur-Norte') NOM_Sentido
  ,DATOS.FECHA
  ,DATOS.VELOCIDAD
  ,DATOS.INTENSIDAD/60
  ,CI.NUM_VELOCIDAD

FROM CI_VELOCIDADES_MEDIAS CI
  ,SGTCONF.ITS_SECTORES S
  ,SGTCONF.ITS_TRAMOS T
  ,SGTCONF.ITS_TRAMOS_EQUIPOS TE
  ,DATOS_PM_2015_01_01 DATOS
  ,SGTCONF.CONF_EQUIPOS CE
  ,SGTCONF.CONF_EQUIPOS_POSICIONES CEP
  
  
WHERE CI.COD_TRAMO = T.NOMBRE_TRAMO
  AND T.COD_SECTOR = S.COD_SECTOR
  AND TE.NOMBRE_TRAMO = T.NOMBRE_TRAMO
  AND DATOS.IDENTIF = TE.IDENT
  AND CI.IND_SENTIDO = TE.SENTIDO
  AND TE.IDENT = CE.IDENT
  AND CE.IDENT = CEP.IDENT
  AND CE.DESCRIPCION <> 'NO EXISTE'
  
  AND TO_DATE(TO_CHAR(CI.FEC_MEDIDA, 'YYYY'), 'YYYY')=TO_DATE('2015', 'YYYY')
  AND DECODE(RTRIM(LTRIM(TO_CHAR(CI.FEC_MEDIDA, 'MONTH'))), 
      'ENERO', 1,'FEBRERO', 2,'MARZO', 3, 'ABRIL', 4, 'MAYO', 5, 'JUNIO', 6, 
      'JULIO',7,'AGOSTO',8, 'SEPTIEMBRE',9, 'OCTUBRE',10,'NOVIEMBRE',11, 'DICIEMBRE',12) = 1
  AND DECODE(RTRIM(LTRIM(TO_CHAR(CI.FEC_MEDIDA, 'DAY'))), 
      'LUNES', 1,'MARTES', 2,'MI�RCOLES', 3, 'JUEVES', 4, 'VIERNES', 5, 'S�BADO', 6, 'DOMINGO',7) BETWEEN 2 AND 4
  AND TO_DATE(TO_CHAR(CI.FEC_MEDIDA, 'HH24:MI'), 'HH24:MI')=TO_DATE('07:30', 'HH24:MI')
  AND CI.COD_TRAMO = 9
  AND S.COD_SECTOR = 5
  AND CI.IND_SENTIDO = '+'
  AND CEP.ID_UBICACION = 1
  AND to_date(to_char(DATOS.FECHA, 'hh24:mi'), 'hh24:mi') > to_date('07:00', 'hh24:mi') 
  AND to_date(to_char(DATOS.FECHA, 'hh24:mi'), 'hh24:mi') <= to_date('07:30', 'hh24:mi')
  AND TO_DATE(TO_CHAR(DATOS.FECHA, 'dd/mm/yyyy'), 'dd/mm/yyyy') = TO_DATE(TO_CHAR(CI.FEC_MEDIDA, 'dd/mm/yyyy'), 'dd/mm/yyyy')
  
ORDER BY CI.FEC_MEDIDA, IDENT,FECHA
;