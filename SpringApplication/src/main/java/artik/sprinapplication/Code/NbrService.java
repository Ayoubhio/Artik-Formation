package artik.sprinapplication.Code;

import org.springframework.stereotype.Service;

@Service
public class NbrService
{
    public int calculateSquare(int number)
    {
        return number * number;
    }
}
