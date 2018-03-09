--this code finds the circular prime numbers from 1 to some input. A number is circularly prime
--if all of its rotations are also prime. Example, The number, 197, is called a circular prime because 
--all rotations of the digits: 197, 971, and 719, are themselves prime.
buildList x = init (rotate (length xs) xs)
                where
                    xs = digits x
                    digits n = map (\x -> read [x] :: Int) (show n)
                    
rotate :: Int -> [Int] -> [[Int]]
rotate 0 _ = [[]]
rotate len (x:xs) = newList : rotate (len-1) newList
                    where
                        newList = xs ++ [x] 

isCircularPrime :: Int -> Bool
isCircularPrime x
    | and (map (isPrime) (map (numbers) list)) = True
    | otherwise = False
    where
        list = buildList x
            

isPrime n = n > 1 && (all (\k -> gcd n k == 1)  (takeWhile (\k -> k*k <= n) [2..]))

numbers :: [Int] -> Integer
numbers as@(x:xs) = sum (zipWith (*) (map (\x -> toInteger (10^x)) [length xs,length xs - 1..0]) (map (toInteger) as))


numCircularPrimes xs = length (filter (isCircularPrime) (filter (isPrime) xs))