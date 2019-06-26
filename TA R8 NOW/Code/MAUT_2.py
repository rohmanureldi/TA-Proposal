import pandas as pd

data = pd.read_excel (r'Lembar Survey.xlsx','Lembar Survey',index_col=None, na_values=['NA'], usecols = "G:Q") 
data.columns
df = pd.DataFrame(data)

produk_index=df.iloc[3:30,0].reset_index(drop=True)
pelayanan_index = df.iloc[30:50,0].reset_index(drop=True)
pengelolaan_index = df.iloc[50:52,0].reset_index(drop=True)


produk_value=[]
pelayanan_value=[]
pengelolaan_value=[]

for i in range(1,df.shape[1]):
    produk_value.append((df.iloc[3:30,i].reset_index(drop=True)).values.astype(int))
    pelayanan_value.append((df.iloc[30:50,i].reset_index(drop=True)).values.astype(int))
    pengelolaan_value.append((df.iloc[50:52,i].reset_index(drop=True)).values.astype(int))

def hitung_bobot(index,listValue,j):
    sum=0
    for i in range (0, len(index)):
        if(index[i]=="M" and listValue[j][i]==1):
            sum+=1
        if(index[i]=="TM" and listValue[j][i]==1):
            sum+=0.5
    return sum


#Menentukan Bobot (Step 2)
produk_bobot=[]
pelayanan_bobot=[]
pengelolaan_bobot=[]
for k in range(0,10):
    produk_bobot.append((hitung_bobot(produk_index,produk_value,k))/20.5)
    pelayanan_bobot.append((hitung_bobot(pelayanan_index,pelayanan_value,k))/14)
    pengelolaan_bobot.append((hitung_bobot(pengelolaan_index,pengelolaan_value,k))/2)

#   !!!!!! Preferensi ini yang akan menjadi inputan user !!!!!!
preferensi=[20/36.5,14/36.5,2/36.5]


matrix = [produk_bobot,pelayanan_bobot,pengelolaan_bobot]

#   Normalisasi Matrix
def normalisasi():
    hasil_normalisasi_array=[]
    for i in range(0,10):
        pre_hasil=0
        for j in range(0,len(preferensi)):
            bobot_alternatif=matrix[j][i]
            x_min=min(matrix[j])
            x_max=max(matrix[j])
            pre_hasil+=preferensi[j]*(bobot_alternatif-x_min)/(x_max+x_min)
            
            
        hasil_normalisasi_array.append([pre_hasil,"hotel "+str(i+1)])
    return hasil_normalisasi_array

hasil_akhir=normalisasi()
hasil_akhir.sort(reverse=True)
