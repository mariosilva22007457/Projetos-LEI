; programa para estudo - Fluxograma
; Zona de definições
; Grupo 5(4ªf 14h)
; Daniel Granja Nº22002469
; José Sobral Nº 22005813
; Mário Silva Nº 22007457

	list p=16F627A
	include p16f627A.inc		; ficheiro de include (procurar na directoria de instalação da Microchip e ver o que tem definido)
 	__CONFIG (_LVP_OFF & _INTRC_OSC_CLKOUT & _WDT_OFF & _PWRTE_ON & _CP_OFF & _MCLRE_ON & _BODEN_OFF)  ; configuraçõs de programação
 	radix dec      ;  (decimal por defeito)

; INPUTS
#define S1 PORTA, 0
#define S2 PORTA, 1
#define SC PORTA, 2
#define MSA PORTA, 3
#define SH1 PORTA, 4
#define SH2 PORTA, 5 
#define HLT PORTA, 6

; OUTPUTS
; PORTB -  NC   V   M  ST2  ST1  ST0    ; NC - not connect
;           5   4   3   2    1    0     bits
#define ESTADO_0    b'00000000'
#define ESTADO_1 	b'00000000' 
#define ESTADO_2 	b'00000000' 
#define ESTADO_3 	b'00000000' 
#define ESTADO_4	b'00001011'
#define ESTADO_5	b'00001011'
#define ESTADO_6	b'00001011'
#define ESTADO_7	b'00000100'
#define ESTADO_8	b'00000100'
#define ESTADO_9	b'00000101'
#define ESTADO_10	b'00000000'
#define ESTADO_11	b'00001000'
#define ESTADO_12	b'00000101'
#define ESTADO_13	b'00010001'
#define ESTADO_14	b'00010001'
#define ESTADO_15	b'00000000'
#define ESTADO_16	b'00010001'
#define ESTADO_17	b'00010001'
#define ESTADO_18	b'00000111'
#define ESTADO_19	b'00011010'
#define ESTADO_20	b'00000100'
#define ESTADO_21	b'00000101'
#define ESTADO_22	b'00000100'
#define ESTADO_23	b'00011010'
#define ESTADO_24	b'00000000'
#define ESTADO_25	b'00011010'
#define ESTADO_26	b'00011010'


; Zona do programa

    org 0

    goto start

    org 0x02

start
    movlw 0x07
    movwf CMCON            ; to activate RA pins for IO - necessário neste PIC

    bsf STATUS,RP0            ; change to register bank 1

    movlw b'00000000'        ; Port B configuration byte. All outs
    movwf TRISB            ; setup port B

    movlw b'11111111'        ; Port A configuration byte. All ins
    movwf TRISA            ; setup port A

	movlw 0x04			; destrancar o Timer0 com prescaler 32:1
	movwf OPTION_REG

    bcf STATUS,RP0            ; change to register bank 0 again


inicio

estado_0
	movlw ESTADO_0
	movwf PORTB			; outputs
	btfss S2			; teste
	goto estado_1		; IN = 0
	goto estado_10		; IN = 1

estado_1
	movlw ESTADO_1
	movwf PORTB
	btfss S1
	goto estado_3
	goto estado_2

estado_2
	movlw ESTADO_2
	movwf PORTB
	btfss HLT
	goto estado_2
	goto estado_2

estado_3
	movlw ESTADO_3
	movwf PORTB
	btfss MSA
	goto estado_4
	goto estado_7

estado_4
	movlw ESTADO_4
	movwf PORTB
	btfss MSA
	goto estado_5
	goto estado_7

estado_5
	movlw ESTADO_5
	movwf PORTB
	btfss S2
	goto estado_6
	goto estado_10

estado_6
	movlw ESTADO_6
	movwf PORTB
	btfss S1
	goto estado_4
	goto estado_2

estado_7
	movlw ESTADO_7
	movwf PORTB
	btfss SH2
	goto estado_8
	goto estado_9

estado_8
	movlw ESTADO_8
	movwf PORTB
	btfss MSA
	goto estado_5
	goto estado_7

estado_9
	movlw ESTADO_9
	movwf PORTB
	btfss HLT
	goto estado_9
	goto estado_9

estado_10
	movlw ESTADO_10
	movwf PORTB
	btfss SH1
	goto estado_11
	goto estado_0

estado_11
	movlw ESTADO_11
	movwf PORTB
	clrf TMR0
estado_11b
	movlw 0x0A
	xorwf TMR0, W
	btfss STATUS, Z
	goto estado_11b
	goto estado_11c

estado_11c
	movlw ESTADO_11
	movwf PORTB
	btfss SH2 
	goto estado_12
	goto estado_13

estado_12
	movlw ESTADO_12
	movwf PORTB
	btfss HLT
	goto estado_12
	goto estado_12
	
estado_13
	movlw ESTADO_13
	movwf PORTB
	btfss S2
	goto estado_17
	goto estado_14

estado_14
	movlw ESTADO_14
	movwf PORTB
	btfss SC
	goto estado_16
	goto estado_15

estado_15
	movlw ESTADO_15
	movwf PORTB
	btfss SC
	goto estado_14
	goto estado_15

estado_16
	movlw ESTADO_16
	movwf PORTB
	btfss SH1
	goto estado_13
	goto estado_10

estado_17
	movlw ESTADO_17
	movwf PORTB
	btfss S1
	goto estado_19
	goto estado_18

estado_18
	movlw ESTADO_18
	movwf PORTB
	btfss HLT
	goto estado_18
	goto estado_18

estado_19
	movlw ESTADO_19
	movwf PORTB
	btfss MSA
	goto estado_23
	goto estado_20

estado_20
	movlw ESTADO_20
	movwf PORTB
	btfss SH2
	goto estado_22
	goto estado_21

estado_21
	movlw ESTADO_21
	movwf PORTB
	btfss HLT
	goto estado_21
	goto estado_21

estado_22
	movlw ESTADO_22
	movwf PORTB
	btfss MSA
	goto estado_23
	goto estado_20

estado_23
	movlw ESTADO_23
	movwf PORTB
	btfss SC
	goto estado_25
	goto estado_24

estado_24
	movlw ESTADO_24
	movwf PORTB
	btfss SC
	goto estado_23
	goto estado_24

estado_25
	movlw ESTADO_25
	movwf PORTB
	btfss S1
	goto estado_26
	goto estado_14

estado_26
	movlw ESTADO_26
	movwf PORTB
	btfss SH1
	goto estado_19
	goto estado_0
	
	END