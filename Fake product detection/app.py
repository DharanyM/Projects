from flask import Flask, render_template, request
from tensorflow.keras.models import load_model
import numpy as np
from tensorflow.keras.preprocessing import image
import tempfile

app = Flask(__name__)
model = load_model('boat.h5')
@app.route('/', methods=['GET'])
def index():
    return render_template('index.html')

@app.route('/upload.html', methods=['GET','POST'])
def home():
    return render_template('upload.html')

@app.route('/predict', methods=['POST'])
def predict():
    # Save the uploaded file to a temporary file
    image_file = request.files['image']
    with tempfile.NamedTemporaryFile(delete=False) as temp:
        image_path = temp.name
        image_file.save(image_path)

    # Load the saved file and make a prediction
    img = image.load_img(image_path, target_size=(64, 64))
    x = image.img_to_array(img)
    x = np.expand_dims(x, axis=1)
    x = x / 255.0  # normalize pixel values to [0, 1]
    # reshape the input to match the expected input shape of the model
    x = np.reshape(x, (1, 64, 64, 3))
    classes = model.predict(x, batch_size=10)
    if np.all(classes[0] <0.7):
        prediction = "Fake"
    else:
        prediction = "Original"

    # Delete the temporary file
    temp.close()
    return prediction

if __name__ == '__main__':
    app.run(debug=True)
