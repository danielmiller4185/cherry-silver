--The decimal number, 585 = 10010010012 (binary), is palindromic in both bases.
--These functions find the sum of all double base palindromes from 1 to 1_000_000
digits n = map (\x -> read [x] :: Int) (show n)

toBinaryReversed :: Int -> [Int]
toBinaryReversed 0 = []
toBinaryReversed x = x `mod` 2 : toBinaryReversed ((x - (x `mod` 2)) `div` 2)

toBinary x = reverse (toBinaryReversed x)

predicate :: Int -> Bool
predicate x
|(toBinary x == reverse (toBinary x)) && (digits x == reverse (digits x)) = True
|otherwise = False

sumOfDoubleBasePalindromes = sum (filter (predicate) [1,3..999999])