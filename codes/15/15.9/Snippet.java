import java.nio.ByteBuffer;

public class Snippet {

	public static void main(String[] args) {
		ByteBuffer buffer = ByteBuffer.allocate(10);
		// �Ը�ByteBufferʵ�����г�ʼ��
		for (int i = 0; i < buffer.capacity(); ++i) {
			buffer.put((byte) i);
		}
		// �޸�buffer��position����㣩��limit���յ㣩
		buffer.position(3);
		buffer.limit(7);
		// �Ի��������з�Ƭ
		ByteBuffer slice = buffer.slice();
		// �Է�Ƭ�����ݽ��в���
		for (int i = 0; i < slice.capacity(); ++i) {
			byte b = slice.get(i);
			b *= 11;
			slice.put(i, b);
		}
		// ���¶�λ��������
		buffer.position(0);
		buffer.limit(buffer.capacity());
		while (buffer.remaining() > 0) {
			System.out.println(buffer.get());
		}

	}

}