/*
 * Copyright 2017 Ettore Carbone.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package Model.Connessione;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * @author Ettore Carbone
 * @version 1.0
 *
 */
public class ModBusTCPPacket {

		private ByteBuffer vet;
		
		static final byte READ_COIL_STATUS=    0x01;
		static final byte READ_INPUT_STATUS=   0x02;
		static final byte READ_HOLDING_REG=    0x03;
		static final byte READ_INPUT_REG=      0x04;
		static final byte FORCE_SINGLE_COIL=   0x05;
		static final byte PRESET_SINGLE_REG=   0x06;
		static final byte READ_STATUS=         0x07;
		static final byte FORCE_MULTIPLE_COILS=0x0F;
		static final byte PRESET_MULTIPLE_REGS=0x10;
		static final byte REPORT_SLAVE_ID=     0x11;
		
		
		/**
		 * Il costruttore inizializza soltanto una struttura utile alla costruzione del pacchetto
		 */
		public ModBusTCPPacket()
		{
			vet=ByteBuffer.allocate(1024);
		}
		
		/**
		 * creazione del pacchetto per le istruzioni che prevedono solo slave id e instruzione e quini riportano solo lo stato
		 * 
		 * @param count: il contatore di messaggi utile per la creazione del preambolo tpc
		 * @param address: l'indirizzo del dispositivo slave con cui comunicare \ 1 byte
		 * @param instruction: il codice istruzione da eseguire \ 1 byte
		 */
		public void initialization (int count, byte address, byte instruction)
		{
			
			if(instruction==READ_STATUS)
			{
				TCPIntestation((short)count, (short)2);
				vet.put(address);
				vet.put(instruction);
			}
			
		}
		
		/**
		 * creazione del pacchetto per le istruzioni di force multiple coils
		 * 
		 * @param count: il contatore di messaggi utile per la creazione del preambolo tpc
		 * @param address: l'indirizzo del dispositivo slave con cui comunicare \ 1 byte
		 * @param instruction: il codice istruzione da eseguire \ 1 byte
		 * @param position: la posizione da cui partire ad eseguire l'operazione\ 2 byte
		 * @param quantiti: la quantit� di byte o word da scrivere \  2 byte
		 * @param data: un array di byte contentente i dati da passare che possono essere usati per accendere dei coil \ x numero di byte
		 */
		public void initialization (int count, byte address, byte instruction, short position, short quantiti, byte[] data)
		{
			
			if(instruction==FORCE_MULTIPLE_COILS)
			{
				int size=sizeSend(quantiti);
				
				TCPIntestation((short)count, (short)(7+size));
				operateField(address, instruction, position, quantiti);
				vet.put((byte)size);
				addArrayField(data, size);
			}
                        else if(instruction==PRESET_MULTIPLE_REGS)
			{
				int size=quantiti;
				
				TCPIntestation((short)count, (short)(7+size));
				operateField(address, instruction, (short) (position/2), (short)(quantiti/2));
				vet.put((byte)size);
				addArrayField(data, size);
			}
		}
		
		
		/**
		 * creazione del pachetto per le istruzioni di read e di Preset Single Reg
		 * 
		 * @param count: il contatore di messaggi utile per la creazione del preambolo tpc
		 * @param address: l'indirizzo del dispositivo slave con cui comunicare \ 1 byte
		 * @param instruction: il codice istruzione da eseguire \ 1 byte
		 * @param position: la posizione da cui partire ad eseguire l'operazione\ 2 byte
		 * @param arg0: un argomento che pu� essere un numero di registri da leggere oppure un dato da scrivere \ 2 byte
		 */
		public void initialization (int count, byte address, byte instruction, short position, short arg0)
		{
					
			if(instruction==READ_COIL_STATUS || instruction==READ_INPUT_STATUS || instruction==READ_HOLDING_REG || instruction==READ_INPUT_REG )
			{
				TCPIntestation((short)count);
				operateField(address, instruction, position, arg0);
			}
			else if(instruction==PRESET_SINGLE_REG)
			{
				TCPIntestation((short)count);
				operateField(address, instruction, position, arg0);
			}
		}
		
		/**
		 * creazione del pacchetto perl'istruzione Force Single Coil
		 * 
		 * @param count: il contatore di messaggi utile per la creazione del preambolo tpc
		 * @param address: l'indirizzo del dispositivo slave con cui comunicare \ 1 byte
		 * @param instruction: il codice istruzione da eseguire \ 1 byte
		 * @param bitPosition: la posizione su cui eseguire l'operazione\ 2 byte
		 * @param operation: pu� essere o ON o OFF \ 1 byte
		 */
		public void initialization (int count, byte address, byte instruction, short  bitPosition, boolean operation)
		{
			
			if(instruction==FORCE_SINGLE_COIL)
			{
				TCPIntestation((short)count);
				vet.put(address);
				vet.put(instruction);
				vet.putShort(bitPosition);
				
				if(operation)
				{
					vet.put((byte)0xFF);
				}
				else
				{
					vet.put((byte)0x00);
				}
				
				vet.put((byte)0x00);
			}
		}
		
		/**
		 * intestazione TCP per le operazioni a byte fissi \ 6 Byte
		 * 
		 * @param count: il conteggio
		 */
		private void TCPIntestation(short count)
		{
			vet.putShort(count);
			vet.put((byte)0x00);
			vet.put((byte)0x00);
			vet.put((byte)0x00);
			vet.put((byte)0x06);
		}
		
		
		/**
		 * intestazione TCP per le operazioni a byte variabili
		 * 
		 * @param count: il conteggio da mettere nell' intestazione tcp
		 * @param dataNumber: il numero di byte variabili da mettere nell'intestazione tcp
		 */
		private void TCPIntestation(short count, short dataNumber)
		{
			vet.putShort(count);
			vet.put((byte)0x00);
			vet.put((byte)0x00);
			vet.putShort(dataNumber);
		}
		
		/**
		 * Metodo per la costruzione dei primi 6 byte del pacchetto
		 * 
		 * @param address: indirizzo dello slave \ 1 byte
		 * @param instruction: codice dell'istruzione da eseguire \ 1 byte
		 * @param startRegister: registro da cui partire ad eseguire l'istruzione \ 2 byte
		 * @param arg0: argomento che dipende dall'istruzione pu� essere il numero di registri da leggere o il dato da scrivere \ 2 byte 
		 */
		private void operateField(byte address,  byte instruction, short startRegister, short arg0) 
		{
			vet.put(address);
			vet.put(instruction);
			vet.putShort(startRegister);
			vet.putShort(arg0);
		}
		
		/**
		 * Metodo per aggiungere un array di dati
		 * 
		 * @param x: array di byte contenente i dati
		 * @param size: grandezza dell' array di dati
		 */
		private void addArrayField(byte[] x, int size)
		{
			for(int i=0;i<size;i++)
			{
				vet.put(x[i]);
			}
		}
		
		/**
		 * Metodo per aggiungere un array di dati
		 * 
		 * @param x: array di short contenente i dati
		 * @param size: grandezza dell' array di dati
		 */
		private void addArrayField(short[] x, int size)
		{
			for(int i=0;i<size;i++)
			{
				vet.putShort(x[i]);
			}
		}
		
		private int sizeSend(short quantiti)
		{
			int x;
			
			if(quantiti%8==0)
			{
				x=quantiti/8;
			}
			else
			{
				x=(quantiti/8)+1;
			}
			
			return x;
			
		}
		
		public byte[] getPacket()
		{
			return Arrays.copyOf(vet.array(), vet.position());
		}
		
		public String getString()
		{
			String s=new String("");
			
			for(int i=0;i<vet.position();i++)
			{
				s=s+" "+Integer.toHexString(Byte.toUnsignedInt(vet.get(i)));
			}
			
			return s;
		}
	
}
	